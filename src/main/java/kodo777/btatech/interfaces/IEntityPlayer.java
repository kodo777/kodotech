package kodo777.btatech.interfaces;

import kodo777.btatech.tileentity.TileEntitySteamBoiler;

public interface IEntityPlayer {
    default void displayGuiSteamBoiler(TileEntitySteamBoiler tileentitysteamboiler) {
        System.out.println("mixin");
    }
}
