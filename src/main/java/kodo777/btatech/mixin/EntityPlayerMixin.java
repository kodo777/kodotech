package kodo777.btatech.mixin;

import kodo777.btatech.KodoGui;
import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamForge;
import kodo777.btatech.tileentity.TileEntitySteamPressingHammer;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin implements KodoGui {
    @Override
    public void kodotech$displayGuiSteamForge(TileEntitySteamForge tileentitysteamforge) {
        //Minecraft.getMinecraft(Minecraft.class).displayGuiScreen(new GuiSteamForge(((EntityPlayerSP) (Object) this).inventory, tileentitysteamforge));
        System.out.println("steam forge");
    }

    @Override
    public void kodotech$displayGuiSteamBoiler(TileEntitySteamBoiler tileentitysteamboiler) {
        //Minecraft.getMinecraft(Minecraft.class).displayGuiScreen(new GuiSteamBoiler(((EntityPlayerSP) (Object) this).inventory, tileentitysteamboiler));
        System.out.println("steam boiler");
    }

    @Override
    public void kodotech$displayGuiSteamPressingHammer(TileEntitySteamPressingHammer tileentitysteampressinghammer) {
        //Minecraft.getMinecraft(Minecraft.class).displayGuiScreen(new GuiSteamPressingHammer(((EntityPlayerSP) (Object) this).inventory, tileentitysteampressinghammer));
        System.out.println("steam pressing hammer");
    }
}
