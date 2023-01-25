package kodo777.btatech.item;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemBucketSteam extends Item {

    public ItemBucketSteam(int i) {
        super(i);
    }

    public static boolean UseBucket(EntityPlayer entityPlayer, ItemStack ItemToGive) {
        if (entityPlayer.inventory.getCurrentItem().stackSize <= 1) {
            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, ItemToGive);
            return true;
        } else if (entityPlayer.inventory.addItemStackToInventory(ItemToGive)) {
            entityPlayer.inventory.getCurrentItem().consumeItem(entityPlayer);
            return true;
        } else {
            return false;
        }
    }
}
