/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.utils.player;

import baritone.api.BaritoneAPI;
import baritone.api.utils.Rotation;
import vince.syshax.SYSHax;
import vince.syshax.mixininterface.IVec3d;
import vince.syshax.systems.config.Config;
import vince.syshax.systems.friends.Friends;
import vince.syshax.systems.modules.Modules;
import vince.syshax.systems.modules.movement.NoFall;
import vince.syshax.utils.Utils;
import vince.syshax.utils.entity.EntityUtils;
import vince.syshax.utils.misc.BaritoneUtils;
import vince.syshax.utils.misc.text.TextUtils;
import vince.syshax.utils.render.color.Color;
import vince.syshax.utils.world.Dimension;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.RaycastContext;

public class PlayerUtils {
    private static final double diagonal = 1 / Math.sqrt(2);
    private static final Vec3d horizontalVelocity = new Vec3d(0, 0, 0);

    private static final Color color = new Color();

    public static Color getPlayerColor(PlayerEntity entity, Color defaultColor) {
        if (Friends.get().isFriend(entity)) {
            return color.set(Config.get().friendColor.get()).a(defaultColor.a);
        }

        if (!color.set(TextUtils.getMostPopularColor(entity.getDisplayName())).equals(Utils.WHITE) && Config.get().useTeamColor.get()) {
            return color.set(color).a(defaultColor.a);
        }

        return defaultColor;
    }

    public static Vec3d getHorizontalVelocity(double bps) {
        float yaw = SYSHax.mc.player.getYaw();

        if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
            Rotation target = BaritoneUtils.getTarget();
            if (target != null) yaw = target.getYaw();
        }

        Vec3d forward = Vec3d.fromPolar(0, yaw);
        Vec3d right = Vec3d.fromPolar(0, yaw + 90);
        double velX = 0;
        double velZ = 0;

        boolean a = false;
        if (SYSHax.mc.player.input.pressingForward) {
            velX += forward.x / 20 * bps;
            velZ += forward.z / 20 * bps;
            a = true;
        }
        if (SYSHax.mc.player.input.pressingBack) {
            velX -= forward.x / 20 * bps;
            velZ -= forward.z / 20 * bps;
            a = true;
        }

        boolean b = false;
        if (SYSHax.mc.player.input.pressingRight) {
            velX += right.x / 20 * bps;
            velZ += right.z / 20 * bps;
            b = true;
        }
        if (SYSHax.mc.player.input.pressingLeft) {
            velX -= right.x / 20 * bps;
            velZ -= right.z / 20 * bps;
            b = true;
        }

        if (a && b) {
            velX *= diagonal;
            velZ *= diagonal;
        }

        ((IVec3d) horizontalVelocity).setXZ(velX, velZ);
        return horizontalVelocity;
    }

    public static void centerPlayer() {
        double x = MathHelper.floor(SYSHax.mc.player.getX()) + 0.5;
        double z = MathHelper.floor(SYSHax.mc.player.getZ()) + 0.5;
        SYSHax.mc.player.setPosition(x, SYSHax.mc.player.getY(), z);
        SYSHax.mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(SYSHax.mc.player.getX(), SYSHax.mc.player.getY(), SYSHax.mc.player.getZ(), SYSHax.mc.player.isOnGround()));
    }

    public static boolean canSeeEntity(Entity entity) {
        Vec3d vec1 = new Vec3d(0, 0, 0);
        Vec3d vec2 = new Vec3d(0, 0, 0);

        ((IVec3d) vec1).set(SYSHax.mc.player.getX(), SYSHax.mc.player.getY() + SYSHax.mc.player.getStandingEyeHeight(), SYSHax.mc.player.getZ());
        ((IVec3d) vec2).set(entity.getX(), entity.getY(), entity.getZ());
        boolean canSeeFeet = SYSHax.mc.world.raycast(new RaycastContext(vec1, vec2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, SYSHax.mc.player)).getType() == HitResult.Type.MISS;

        ((IVec3d) vec2).set(entity.getX(), entity.getY() + entity.getStandingEyeHeight(), entity.getZ());
        boolean canSeeEyes = SYSHax.mc.world.raycast(new RaycastContext(vec1, vec2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, SYSHax.mc.player)).getType() == HitResult.Type.MISS;

        return canSeeFeet || canSeeEyes;
    }

    public static float[] calculateAngle(Vec3d target) {
        Vec3d eyesPos = new Vec3d(SYSHax.mc.player.getX(), SYSHax.mc.player.getY() + SYSHax.mc.player.getEyeHeight(SYSHax.mc.player.getPose()), SYSHax.mc.player.getZ());

        double dX = target.x - eyesPos.x;
        double dY = (target.y - eyesPos.y) * -1.0D;
        double dZ = target.z - eyesPos.z;

        double dist = Math.sqrt(dX * dX + dZ * dZ);

        return new float[]{(float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(dZ, dX)) - 90.0D), (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(dY, dist)))};
    }

    public static boolean shouldPause(boolean ifBreaking, boolean ifEating, boolean ifDrinking) {
        if (ifBreaking && SYSHax.mc.interactionManager.isBreakingBlock()) return true;
        if (ifEating && (SYSHax.mc.player.isUsingItem() && (SYSHax.mc.player.getMainHandStack().getItem().isFood() || SYSHax.mc.player.getOffHandStack().getItem().isFood()))) return true;
        return ifDrinking && (SYSHax.mc.player.isUsingItem() && (SYSHax.mc.player.getMainHandStack().getItem() instanceof PotionItem || SYSHax.mc.player.getOffHandStack().getItem() instanceof PotionItem));
    }

    public static boolean isMoving() {
        return SYSHax.mc.player.forwardSpeed != 0 || SYSHax.mc.player.sidewaysSpeed != 0;
    }

    public static boolean isSprinting() {
        return SYSHax.mc.player.isSprinting() && (SYSHax.mc.player.forwardSpeed != 0 || SYSHax.mc.player.sidewaysSpeed != 0);
    }

    public static boolean isInHole(boolean doubles) {
        if (!Utils.canUpdate()) return false;

        BlockPos blockPos = SYSHax.mc.player.getBlockPos();
        int air = 0;

        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP) continue;

            BlockState state = SYSHax.mc.world.getBlockState(blockPos.offset(direction));

            if (state.getBlock().getBlastResistance() < 600) {
                if (!doubles || direction == Direction.DOWN) return false;

                air++;

                for (Direction dir : Direction.values()) {
                    if (dir == direction.getOpposite() || dir == Direction.UP) continue;

                    BlockState blockState1 = SYSHax.mc.world.getBlockState(blockPos.offset(direction).offset(dir));

                    if (blockState1.getBlock().getBlastResistance() < 600) {
                        return false;
                    }
                }
            }
        }

        return air < 2;
    }

    public static double possibleHealthReductions() {
        return possibleHealthReductions(true, true);
    }

    public static double possibleHealthReductions(boolean entities, boolean fall) {
        double damageTaken = 0;

        if (entities) {
            for (Entity entity : SYSHax.mc.world.getEntities()) {
                // Check for end crystals
                if (entity instanceof EndCrystalEntity && damageTaken < DamageUtils.crystalDamage(SYSHax.mc.player, entity.getPos())) {
                    damageTaken = DamageUtils.crystalDamage(SYSHax.mc.player, entity.getPos());
                }
                // Check for players holding swords
                else if (entity instanceof PlayerEntity && damageTaken < DamageUtils.getSwordDamage((PlayerEntity) entity, true)) {
                    if (!Friends.get().isFriend((PlayerEntity) entity) && SYSHax.mc.player.getPos().distanceTo(entity.getPos()) < 5) {
                        if (((PlayerEntity) entity).getActiveItem().getItem() instanceof SwordItem) {
                            damageTaken = DamageUtils.getSwordDamage((PlayerEntity) entity, true);
                        }
                    }
                }
            }

            // Check for beds if in nether
            if (PlayerUtils.getDimension() != Dimension.Overworld) {
                for (BlockEntity blockEntity : Utils.blockEntities()) {
                    BlockPos bp = blockEntity.getPos();
                    Vec3d pos = new Vec3d(bp.getX(), bp.getY(), bp.getZ());

                    if (blockEntity instanceof BedBlockEntity && damageTaken < DamageUtils.bedDamage(SYSHax.mc.player, pos)) {
                        damageTaken = DamageUtils.bedDamage(SYSHax.mc.player, pos);
                    }
                }
            }
        }

        // Check for fall distance with water check
        if (fall) {
            if (!Modules.get().isActive(NoFall.class) && SYSHax.mc.player.fallDistance > 3) {
                double damage = SYSHax.mc.player.fallDistance * 0.5;

                if (damage > damageTaken && !EntityUtils.isAboveWater(SYSHax.mc.player)) {
                    damageTaken = damage;
                }
            }
        }

        return damageTaken;
    }

    public static double distanceTo(Entity entity) {
        return distanceTo(entity.getX(), entity.getY(), entity.getZ());
    }

    public static double distanceTo(BlockPos blockPos) {
        return distanceTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static double distanceTo(Vec3d vec3d) {
        return distanceTo(vec3d.getX(), vec3d.getY(), vec3d.getZ());
    }

    public static double distanceTo(double x, double y, double z) {
        float f = (float) (SYSHax.mc.player.getX() - x);
        float g = (float) (SYSHax.mc.player.getY() - y);
        float h = (float) (SYSHax.mc.player.getZ() - z);
        return MathHelper.sqrt(f * f + g * g + h * h);
    }

    public static double distanceToCamera(double x, double y, double z) {
        Camera camera = SYSHax.mc.gameRenderer.getCamera();
        return Math.sqrt(camera.getPos().squaredDistanceTo(x, y, z));
    }

    public static double distanceToCamera(Entity entity) {
        return distanceToCamera(entity.getX(), entity.getY(), entity.getZ());
    }

    public static Dimension getDimension() {
        if (SYSHax.mc.world == null) return Dimension.Overworld;

        return switch (SYSHax.mc.world.getRegistryKey().getValue().getPath()) {
            case "the_nether" -> Dimension.Nether;
            case "the_end" -> Dimension.End;
            default -> Dimension.Overworld;
        };
    }

    public static GameMode getGameMode() {
        PlayerListEntry playerListEntry = SYSHax.mc.getNetworkHandler().getPlayerListEntry(SYSHax.mc.player.getUuid());
        if (playerListEntry == null) return GameMode.SPECTATOR;
        return playerListEntry.getGameMode();
    }

    public static double getTotalHealth() {
        return SYSHax.mc.player.getHealth() + SYSHax.mc.player.getAbsorptionAmount();
    }

    public static boolean isAlive() {
        return SYSHax.mc.player.isAlive() && !SYSHax.mc.player.isDead();
    }

    public static int getPing() {
        if (SYSHax.mc.getNetworkHandler() == null) return 0;

        PlayerListEntry playerListEntry = SYSHax.mc.getNetworkHandler().getPlayerListEntry(SYSHax.mc.player.getUuid());
        if (playerListEntry == null) return 0;
        return playerListEntry.getLatency();
    }
}
