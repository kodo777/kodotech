package kodo777.btatech.container;

import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.Iterator;
import java.util.List;

public class ContainerSteamBoiler extends Container {
    private TileEntitySteamBoiler steamBoiler;
    private int currentCookTime = 0;
    private int currentBurnTime = 0;
    private int itemBurnTime = 0;
    private int itemCookTime = 0;

    public ContainerSteamBoiler(IInventory inventoryplayer, TileEntitySteamBoiler tileentitysteamboiler) {
        this.steamBoiler = tileentitysteamboiler;
        this.addSlot(new Slot(tileentitysteamboiler, 0, 56, 17));
        this.addSlot(new Slot(tileentitysteamboiler, 1, 56, 53));
        this.addSlot(new Slot(tileentitysteamboiler, 2, 116, 35));

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
}
