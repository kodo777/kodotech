package kodo777.btatech.interfaces;

import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamForge;
import kodo777.btatech.tileentity.TileEntitySteamPressingHammer;
import net.minecraft.src.GuiScreen;

public interface IEntityPlayerSP {
    default void displayGui(GuiScreen gui){}
    /*default void displayGuiSteamForge(TileEntitySteamForge tileentitysteamforge) {}
    default void displayGuiSteamPressingHammer(TileEntitySteamPressingHammer tileentitysteampressinghammer) {}
    default void displayGuiSteamBoiler(TileEntitySteamBoiler tileentitysteamboiler) {}*/
}
