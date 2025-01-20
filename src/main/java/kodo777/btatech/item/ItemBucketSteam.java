package kodo777.btatech.item;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;

public class ItemBucketSteam extends Item {
    public ItemBucketSteam(String name, int i) {
        super(name, i);
    }

    public static boolean UseBucket(EntityPlayer entityPlayer, ItemStack ItemToGive) {
        if (entityPlayer.inventory.getCurrentItem().stackSize <= 1) {
            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, ItemToGive);
            return true;
        }
        entityPlayer.dropPlayerItem(ItemToGive);
        entityPlayer.inventory.getCurrentItem().consumeItem(entityPlayer);
        return true;
    }
}
