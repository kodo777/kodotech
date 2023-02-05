package kodo777.btatech.container;

import kodo777.btatech.BtATech;
import kodo777.btatech.tileentity.TileEntitySteamForge;
import net.minecraft.src.*;

import java.util.Iterator;

public class ContainerSteamForge extends Container {
    private TileEntitySteamForge steamForge;
    private int currentCookTime = 0;
    private int currentBurnTime = 0;
    private int itemBurnTime = 0;
    private int itemCookTime = 0;

    public ContainerSteamForge(InventoryPlayer inventoryplayer, TileEntitySteamForge tileentitysteamforge) {
        this.steamForge = tileentitysteamforge;
        this.addSlot(new Slot(tileentitysteamforge, 0, 48, 15));
        this.addSlot(new Slot(tileentitysteamforge, 1, 48, 55));
        this.addSlot(new SlotSteamForge(inventoryplayer.player, tileentitysteamforge, 2, 127, 35));
        this.addSlot(new Slot(tileentitysteamforge, 3, 28, 35));
        this.addSlot(new Slot(tileentitysteamforge, 4, 68, 35));

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
            if (this.currentCookTime != this.steamForge.currentCookTime) {
                icrafting.updateCraftingInventoryInfo(this, 0, this.steamForge.currentCookTime);
            }

            if (this.currentBurnTime != this.steamForge.currentBurnTime) {
                icrafting.updateCraftingInventoryInfo(this, 1, this.steamForge.currentBurnTime);
            }

            if (this.itemCookTime != this.steamForge.maxCookTime) {
                icrafting.updateCraftingInventoryInfo(this, 2, this.steamForge.maxCookTime);
            }

            if (this.itemBurnTime != this.steamForge.maxBurnTime) {
                icrafting.updateCraftingInventoryInfo(this, 3, this.steamForge.maxBurnTime);
            }
        }

        this.currentCookTime = this.steamForge.currentCookTime;
        this.currentBurnTime = this.steamForge.currentBurnTime;
        this.itemCookTime = this.steamForge.maxCookTime;
        this.itemBurnTime = this.steamForge.maxBurnTime;
    }

    public void updateClientProgressBar(int id, int value) {
        if (id == 0) {
            this.steamForge.currentCookTime = value;
        }

        if (id == 1) {
            this.steamForge.currentBurnTime = value;
        }

        if (id == 2) {
            this.steamForge.maxCookTime = value;
        }

        if (id == 3) {
            this.steamForge.maxBurnTime = value;
        }

    }

    public boolean isUsableByPlayer(EntityPlayer entityplayer) {
        return this.steamForge.canInteractWith(entityplayer);
    }

    public void quickMoveItems(int slotID, EntityPlayer player, boolean shift, boolean control) {
        Slot slot = (Slot)this.inventorySlots.get(slotID);
        if (slot != null && slot.hasStack()) {
            ItemStack item = slot.getStack();
            ItemStack originalItem = item.copy();
            if (slotID < 3 || (originalItem.itemID >= Block.blocksList.length || Block.blocksList[originalItem.itemID].blockMaterial != Material.wood) && originalItem.itemID != Item.stick.itemID && originalItem.itemID != Item.coal.itemID && originalItem.itemID != Item.nethercoal.itemID && originalItem.itemID != Item.olivine.itemID && originalItem.itemID != Item.bucketLava.itemID && originalItem.itemID != Block.saplingOak.blockID && originalItem.itemID != Block.blockCoal.blockID && originalItem.itemID != Block.blockCharcoal.blockID && originalItem.itemID != Block.blockNetherCoal.blockID && originalItem.itemID != Block.blockOlivine.blockID && originalItem.itemID != BtATech.bucketSteam.itemID) {
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
