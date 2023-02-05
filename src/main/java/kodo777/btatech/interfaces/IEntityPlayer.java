package kodo777.btatech.interfaces;

import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamForge;
import kodo777.btatech.tileentity.TileEntitySteamPressingHammer;
import net.minecraft.src.GuiScreen;

public interface IEntityPlayer {
    default void displayGui(GuiScreen gui){}
    /*default void displayGuiSteamForge(TileEntitySteamForge tileentitysteamforge) {
        System.out.println("forge");
    }
    default void displayGuiSteamPressingHammer(TileEntitySteamPressingHammer tileentitysteampressinghammer) { System.out.println("pressing hammer"); }
    default void displayGuiSteamBoiler(TileEntitySteamBoiler tileentitysteamboiler) {
        System.out.println("boiler");
    }*/
}
