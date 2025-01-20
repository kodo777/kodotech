package kodo777.btatech.container;

import kodo777.btatech.BtATech;
import kodo777.btatech.tileentity.TileEntitySteamForge;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.Iterator;
import java.util.List;

public class ContainerSteamForge extends Container {
    private TileEntitySteamForge steamForge;
    private int currentCookTime = 0;
    private int currentBurnTime = 0;
    private int itemBurnTime = 0;
    private int itemCookTime = 0;

    public ContainerSteamForge(IInventory inventoryplayer, TileEntitySteamForge tileentitysteamforge) {
        this.steamForge = tileentitysteamforge;
        this.addSlot(new Slot(tileentitysteamforge, 0, 48, 15));
        this.addSlot(new Slot(tileentitysteamforge, 1, 48, 55));
        this.addSlot(new Slot(tileentitysteamforge, 2, 127, 35));
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

    @Override
    public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        return null;
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
}
