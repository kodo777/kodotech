package kodo777.btatech.container;

import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import net.minecraft.src.*;

import java.util.Iterator;

public class ContainerSteamBoiler extends Container {
    private TileEntitySteamBoiler steamBoiler;
    private int currentCookTime = 0;
    private int currentBurnTime = 0;
    private int itemBurnTime = 0;
    private int itemCookTime = 0;

    public ContainerSteamBoiler(InventoryPlayer inventoryplayer, TileEntitySteamBoiler tileentitysteamboiler) {
        this.steamBoiler = tileentitysteamboiler;
        this.addSlot(new Slot(tileentitysteamboiler, 0, 56, 17));
        this.addSlot(new Slot(tileentitysteamboiler, 1, 56, 53));
        this.addSlot(new SlotSteamBoiler(inventoryplayer.player, tileentitysteamboiler, 2, 116, 35));

        int j;
        for(j = 0; j < 3; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventoryplayer, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for(j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }

    }

    public void updateInventory() {
        super.updateInventory();
        Iterator var1 = this.crafters.iterator();

        while(var1.hasNext()) {
            Object crafter = var1.next();
            ICrafting icrafting = (ICrafting)crafter;
            if (this.currentCookTime != this.steamBoiler.currentCookTime) {
                icrafting.updateCraftingInventoryInfo(this, 0, this.steamBoiler.currentCookTime);
            }

            if (this.currentBurnTime != this.steamBoiler.currentBurnTime) {
                icrafting.updateCraftingInventoryInfo(this, 1, this.steamBoiler.currentBurnTime);
            }

            if (this.itemCookTime != this.steamBoiler.maxCookTime) {
                icrafting.updateCraftingInventoryInfo(this, 2, this.steamBoiler.maxCookTime);
            }

            if (this.itemBurnTime != this.steamBoiler.maxBurnTime) {
                icrafting.updateCraftingInventoryInfo(this, 3, this.steamBoiler.maxBurnTime);
            }
        }

        this.currentCookTime = this.steamBoiler.currentCookTime;
        this.currentBurnTime = this.steamBoiler.currentBurnTime;
        this.itemCookTime = this.steamBoiler.maxCookTime;
        this.itemBurnTime = this.steamBoiler.maxBurnTime;
    }

    public void updateClientProgressBar(int id, int value) {
        if (id == 0) {
            this.steamBoiler.currentCookTime = value;
        }

        if (id == 1) {
            this.steamBoiler.currentBurnTime = value;
        }

        if (id == 2) {
            this.steamBoiler.maxCookTime = value;
        }

        if (id == 3) {
            this.steamBoiler.maxBurnTime = value;
        }

    }

    public boolean isUsableByPlayer(EntityPlayer entityplayer) {
        return this.steamBoiler.canInteractWith(entityplayer);
    }

    public void quickMoveItems(int slotID, EntityPlayer player, boolean shift, boolean control) {
        Slot slot = (Slot)this.inventorySlots.get(slotID);
        if (slot != null && slot.hasStack()) {
            ItemStack item = slot.getStack();
            ItemStack originalItem = item.copy();
            if (slotID < 3 || (originalItem.itemID >= Block.blocksList.length || Block.blocksList[originalItem.itemID].blockMaterial != Material.wood) && originalItem.itemID != Item.stick.itemID && originalItem.itemID != Item.coal.itemID && originalItem.itemID != Item.nethercoal.itemID && originalItem.itemID != Item.olivine.itemID && originalItem.itemID != Item.bucketLava.itemID && originalItem.itemID != Block.saplingOak.blockID && originalItem.itemID != Block.blockCoal.blockID && originalItem.itemID != Block.blockCharcoal.blockID && originalItem.itemID != Block.blockNetherCoal.blockID && originalItem.itemID != Block.blockOlivine.blockID) {
                if (slotID == 2) {
                    this.onStackMergeShiftClick(item, 3, 39, true);
                } else if (slotID >= 3) {
                    this.onStackMergeShiftClick(item, 0, 1, false);
                } else {
                    this.onStackMergeShiftClick(item, 3, 39, false);
                }
            } else {
                this.onStackMergeShiftClick(item, 1, 2, false);
            }

            if (item.stackSize == 0) {
                slot.putStack((ItemStack)null);
            } else {
                slot.onSlotChanged();
            }

            if (item.stackSize != originalItem.stackSize) {
                slot.onPickupFromSlot(originalItem);
            }

        }
    }
}
