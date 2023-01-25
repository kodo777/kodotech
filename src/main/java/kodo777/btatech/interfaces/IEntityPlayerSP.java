package kodo777.btatech.interfaces;

import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamPressingHammer;

public interface IEntityPlayerSP {
    default void displayGuiSteamPressingHammer(TileEntitySteamPressingHammer tileentitysteampressinghammer) {}
    default void displayGuiSteamBoiler(TileEntitySteamBoiler tileentitysteamboiler) {}
}
