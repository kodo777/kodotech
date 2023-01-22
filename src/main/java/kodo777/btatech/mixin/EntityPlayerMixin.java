package kodo777.btatech.mixin;

import kodo777.btatech.interfaces.IEntityPlayer;
import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import net.minecraft.src.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin implements IEntityPlayer {

    @Override
    public void displayGuiSteamBoiler(TileEntitySteamBoiler tileentitysteamboiler){
        System.out.println("mixin");
    }
}
