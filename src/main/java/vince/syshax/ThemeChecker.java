/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax;

import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;
import vince.syshax.gui.GuiThemes;

public class ThemeChecker {
    public static void ThemeCeck() {
        final RichPresence rpc = new RichPresence();

        if (GuiThemes.get().name.equals("TrapHax")) {

            DiscordIPC.start(1014874602314412092L, null);

            rpc.setStart(System.currentTimeMillis() / 1000L);

            String largeText = "Trap Client " + SYSHax.VERSION;
            if (!SYSHax.DEV_BUILD.isEmpty()) largeText += " Dev Build: " + SYSHax.DEV_BUILD;
            rpc.setLargeImage("icon", largeText);



            rpc.setDetails("TrapHax Client " + (SYSHax.DEV_BUILD.isEmpty() ? SYSHax.VERSION : SYSHax.VERSION + " " + SYSHax.DEV_BUILD));

            rpc.setState("Traps are really hot!");
        } else {
            DiscordIPC.start(1014874602314412092L, null);

            rpc.setStart(System.currentTimeMillis() / 1000L);







            rpc.setDetails("SYSHAX " + (SYSHax.DEV_BUILD.isEmpty() ? SYSHax.VERSION : SYSHax.VERSION + " " + SYSHax.DEV_BUILD));

            rpc.setState("SYSHax Is The Best!");


        }






    }

}
