/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.mixin;

import vince.syshax.systems.config.Config;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {
    private boolean override = true;
    private final Random random = new Random();

    private final List<String> meteorSplashes = getMeteorSplashes();

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void onApply(CallbackInfoReturnable<String> cir) {
        if (Config.get() == null || !Config.get().titleScreenSplashes.get()) return;

        if (override) cir.setReturnValue(meteorSplashes.get(random.nextInt(meteorSplashes.size())));
        override = !override;
    }

    private static List<String> getMeteorSplashes() {
        return Arrays.asList(
                "§dAstolfo§r Is Hot!",
                "§4Trir§r is hot!",
                "§dTraps§r are hot.",
                "§dVince Productions!",
                "§dLol IMAGINE",
                "§dKurwa",
                "§5Meteor Fork",
                "§dDont Vape!",
                "§dBort Med Borås - Vince",
                "§4Start Fapping To Guys Faster Win Rate - Trir",
                "Sigma HATAR?",
                "§2Jesse we need to cook",
                "§bWalter put your dick away walter",
                "§bIm not having sex with you right now walter",
                "§2I am the one who knocks",
                "§9Club Penguin is kil",
                "§6The Bird is the word",
                "§9Слава§r §eУкраїні",
                "§9hacks:gethacks() - JollyC",
                "§dSkriv här för att söka - Vince's Computer",
                "§4Overclocked Your Ram to 69000 Megashits per second",
                "§4Gta Sex mod"



        );
    }

}
