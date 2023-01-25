package kodo777.btatech.mixin;

import kodo777.btatech.gui.GuiSteamBoiler;
import kodo777.btatech.gui.GuiSteamPressingHammer;
import kodo777.btatech.interfaces.IEntityPlayerSP;
import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamPressingHammer;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntityPlayerSP.class, remap = false)
public abstract class EntityPlayerSPMixin implements IEntityPlayerSP {

    @Override
    public void displayGuiSteamBoiler(TileEntitySteamBoiler tileentitysteamboiler){
        Minecraft.getMinecraft().displayGuiScreen(new GuiSteamBoiler(((EntityPlayerSP)(Object)this).inventory, tileentitysteamboiler));
    }
    @Override
    public void displayGuiSteamPressingHammer(TileEntitySteamPressingHammer tileentitysteampressinghammer){
        Minecraft.getMinecraft().displayGuiScreen(new GuiSteamPressingHammer(((EntityPlayerSP)(Object)this).inventory, tileentitysteampressinghammer));
    }
}
