package kodo777.btatech.mixin;

import kodo777.btatech.gui.GuiSteamBoiler;
import kodo777.btatech.gui.GuiSteamForge;
import kodo777.btatech.gui.GuiSteamPressingHammer;
import kodo777.btatech.interfaces.IEntityPlayerSP;
import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamForge;
import kodo777.btatech.tileentity.TileEntitySteamPressingHammer;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EntityPlayerSP.class, remap = false)
public abstract class EntityPlayerSPMixin implements IEntityPlayerSP {
    @Shadow
    protected Minecraft mc;
    @Override
    public void displayGui(GuiScreen gui){this.mc.displayGuiScreen(gui);}
    /*@Override
    public void displayGuiSteamForge(TileEntitySteamForge tileentitysteamforge){
        Minecraft.getMinecraft().displayGuiScreen(new GuiSteamForge(((EntityPlayerSP)(Object)this).inventory, tileentitysteamforge));
        System.out.println("steam forge");
    }
    @Override
    public void displayGuiSteamBoiler(TileEntitySteamBoiler tileentitysteamboiler){
        Minecraft.getMinecraft().displayGuiScreen(new GuiSteamBoiler(((EntityPlayerSP)(Object)this).inventory, tileentitysteamboiler));
        System.out.println("steam boiler");
    }
    @Override
    public void displayGuiSteamPressingHammer(TileEntitySteamPressingHammer tileentitysteampressinghammer){
        Minecraft.getMinecraft().displayGuiScreen(new GuiSteamPressingHammer(((EntityPlayerSP)(Object)this).inventory, tileentitysteampressinghammer));
        System.out.println("steam pressing hammer");
    }*/
}
