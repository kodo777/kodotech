package kodo777.btatech.mixin;

import kodo777.btatech.KodoGui;
import kodo777.btatech.gui.GuiSteamBoiler;
import kodo777.btatech.gui.GuiSteamForge;
import kodo777.btatech.gui.GuiSteamPressingHammer;
import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamForge;
import kodo777.btatech.tileentity.TileEntitySteamPressingHammer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntityPlayerSP.class, remap = false)
public abstract class EntityPlayerSPMixin implements KodoGui {
    @Override
    public void kodotech$displayGuiSteamForge(TileEntitySteamForge tileentitysteamforge) {
        Minecraft.getMinecraft(Minecraft.class).displayGuiScreen(new GuiSteamForge(((EntityPlayerSP) (Object) this).inventory, tileentitysteamforge));
    }

    @Override
    public void kodotech$displayGuiSteamBoiler(TileEntitySteamBoiler tileentitysteamboiler) {
        Minecraft.getMinecraft(Minecraft.class).displayGuiScreen(new GuiSteamBoiler(((EntityPlayerSP) (Object) this).inventory, tileentitysteamboiler));
    }

    @Override
    public void kodotech$displayGuiSteamPressingHammer(TileEntitySteamPressingHammer tileentitysteampressinghammer) {
        Minecraft.getMinecraft(Minecraft.class).displayGuiScreen(new GuiSteamPressingHammer(((EntityPlayerSP) (Object) this).inventory, tileentitysteampressinghammer));
    }
}
