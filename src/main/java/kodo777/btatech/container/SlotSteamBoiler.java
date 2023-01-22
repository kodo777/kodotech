package kodo777.btatech.container;

import net.minecraft.src.*;

public class SlotSteamBoiler extends Slot {
    private EntityPlayer thePlayer;

    public SlotSteamBoiler(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k) {
        super(iinventory, i, j, k);
        this.thePlayer = entityplayer;
    }

    public boolean canPutStackInSlot(ItemStack itemstack) {
        return false;
    }

    public void onPickupFromSlot(ItemStack itemstack) {
        itemstack.onCrafting(this.thePlayer.worldObj, this.thePlayer);
        if (itemstack.itemID == Item.ingotIron.itemID) {
            this.thePlayer.addStat(AchievementList.acquireIron, 1);
        }

        if (itemstack.itemID == Item.foodFishCooked.itemID) {
            this.thePlayer.addStat(AchievementList.cookFish, 1);
        }

        super.onPickupFromSlot(itemstack);
    }
}
