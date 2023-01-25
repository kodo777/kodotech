package kodo777.btatech.interfaces;

import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamPressingHammer;

public interface IEntityPlayer {
    default void displayGuiSteamPressingHammer(TileEntitySteamPressingHammer tileentitysteampressinghammer) { System.out.println("mixin"); }
    default void displayGuiSteamBoiler(TileEntitySteamBoiler tileentitysteamboiler) {
        System.out.println("mixin");
    }

}
