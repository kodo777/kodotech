package kodo777.btatech.tileentity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import kodo777.btatech.BtATech;
import kodo777.btatech.block.BlockSteamBoiler;
import kodo777.btatech.recipe.RecipesSteamBoiler;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.crafting.LookupFuelFurnaceBlast;
import net.minecraft.core.player.inventory.IInventory;

public class TileEntitySteamBoiler extends TileEntity implements IInventory {
    protected ItemStack[] steamBoilerItemStacks = new ItemStack[3];
    public int maxBurnTime = 0;
    public int currentCookTime = 0;
    public int maxCookTime = 200;
    public int currentBurnTime = 0;
    public TileEntitySteamBoiler(){
    }

    @Override
    public int getSizeInventory() {
        return this.steamBoilerItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.steamBoilerItemStacks[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (this.steamBoilerItemStacks[i] != null) {
            ItemStack itemstack1;
            if (this.steamBoilerItemStacks[i].stackSize <= j) {
                itemstack1 = this.steamBoilerItemStacks[i];
                this.steamBoilerItemStacks[i] = null;
                return itemstack1;
            } else {
                itemstack1 = this.steamBoilerItemStacks[i].splitStack(j);
                if (this.steamBoilerItemStacks[i].stackSize == 0) {
                    this.steamBoilerItemStacks[i] = null;
                }

                return itemstack1;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.steamBoilerItemStacks[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

    }

    @Override
    public String getInvName() {
        return "Steam Boiler";
    }

    @Override
    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getList("Items");
        this.steamBoilerItemStacks = new ItemStack[this.getSizeInventory()];

        for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            CompoundTag nbttagcompound1 = (CompoundTag)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.steamBoilerItemStacks.length) {
                this.steamBoilerItemStacks[byte0] = ItemStack.readItemStackFromNbt(nbttagcompound1);
            }
        }

        this.currentBurnTime = nbttagcompound.getShort("BurnTime");
        this.currentCookTime = nbttagcompound.getShort("CookTime");
        this.maxBurnTime = nbttagcompound.getShort("MaxBurnTime");
    }

    @Override
    public void writeToNBT(CompoundTag nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.putShort("BurnTime", (short)this.currentBurnTime);
        nbttagcompound.putShort("CookTime", (short)this.currentCookTime);
        nbttagcompound.putShort("MaxBurnTime", (short)this.maxBurnTime);
        ListTag nbttaglist = new ListTag();

        for(int i = 0; i < this.steamBoilerItemStacks.length; ++i) {
            if (this.steamBoilerItemStacks[i] != null) {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.putByte("Slot", (byte)i);
                this.steamBoilerItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.addTag(nbttagcompound1);
            }
        }

        nbttagcompound.put("Items", nbttaglist);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public int getCookProgressScaled(int i) {
        return this.maxCookTime == 0 ? 0 : this.currentCookTime * i / this.maxCookTime;
    }

    public int getBurnTimeRemainingScaled(int i) {
        return this.maxBurnTime == 0 ? 0 : this.currentBurnTime * i / this.maxBurnTime;
    }

    public boolean isBurning() {
        return this.currentBurnTime > 0;
    }

    @Override
    public void tick() {
        boolean isBurnTimeHigherThan0 = this.currentBurnTime > 0;
        boolean steamBoilerUpdated = false;
        if (this.currentBurnTime > 0) {
            --this.currentBurnTime;
        }

        if (!this.worldObj.isClientSide) {
            if (this.worldObj.getBlockId(this.x, this.y, this.z) == BtATech.steamBoilerIdle.id && this.currentBurnTime == 0 && this.steamBoilerItemStacks[0] == null && this.steamBoilerItemStacks[1] != null && this.steamBoilerItemStacks[1].itemID == Block.netherrack.id) {
                --this.steamBoilerItemStacks[1].stackSize;
                if (this.steamBoilerItemStacks[1].stackSize == 0) {
                    this.steamBoilerItemStacks[1] = null;
                }

                BlockSteamBoiler.updateSteamBoilerBlockState(true, this.worldObj, this.x, this.y, this.z);
                steamBoilerUpdated = true;
            }

            if (this.currentBurnTime == 0 && this.canSmelt()) {
                this.maxBurnTime = this.currentBurnTime = this.getBurnTimeFromItem(this.steamBoilerItemStacks[1]);
                if (this.currentBurnTime > 0) {
                    steamBoilerUpdated = true;
                    if (this.steamBoilerItemStacks[1] != null) {
                        if (this.steamBoilerItemStacks[1].getItem() == Item.bucketLava) {
                            this.steamBoilerItemStacks[1] = new ItemStack(Item.bucket);
                        } else {
                            --this.steamBoilerItemStacks[1].stackSize;
                            if (this.steamBoilerItemStacks[1].stackSize == 0) {
                                this.steamBoilerItemStacks[1] = null;
                            }
                        }
                    }
                }
            }

            if (this.isBurning() && this.canSmelt()) {
                ++this.currentCookTime;
                if (this.currentCookTime == this.maxCookTime) {
                    this.currentCookTime = 0;
                    this.smeltItem();
                    steamBoilerUpdated = true;
                }
            } else {
                this.currentCookTime = 0;
            }

            if (isBurnTimeHigherThan0 != this.currentBurnTime > 0) {
                steamBoilerUpdated = true;
                this.updateSteamBoiler();
            }
        }

        if (steamBoilerUpdated) {
            this.onInventoryChanged();
        }

    }

    private boolean canSmelt() {
        if (this.steamBoilerItemStacks[0] == null) {
            return false;
        } else {
            ItemStack itemstack = RecipesSteamBoiler.smelting().getSmeltingResult(this.steamBoilerItemStacks[0].getItem().id);
            if (itemstack == null) {
                return false;
            } else if (this.steamBoilerItemStacks[2] == null) {
                return true;
            } else if (!this.steamBoilerItemStacks[2].isItemEqual(itemstack)) {
                return false;
            } else if (this.steamBoilerItemStacks[2].stackSize < this.getInventoryStackLimit() && this.steamBoilerItemStacks[2].stackSize < this.steamBoilerItemStacks[2].getMaxStackSize()) {
                return true;
            } else {
                return this.steamBoilerItemStacks[2].stackSize < itemstack.getMaxStackSize();
            }
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemstack = RecipesSteamBoiler.smelting().getSmeltingResult(this.steamBoilerItemStacks[0].getItem().id);
            if (this.steamBoilerItemStacks[2] == null) {
                this.steamBoilerItemStacks[2] = itemstack.copy();
            } else if (this.steamBoilerItemStacks[2].itemID == itemstack.itemID) {
                ++this.steamBoilerItemStacks[2].stackSize;
            }

            --this.steamBoilerItemStacks[0].stackSize;
            if (this.steamBoilerItemStacks[0].stackSize <= 0) {
                this.steamBoilerItemStacks[0] = null;
            }

        }
    }

    protected void updateSteamBoiler() {
        BlockSteamBoiler.updateSteamBoilerBlockState(this.currentBurnTime > 0, this.worldObj, this.x, this.y, this.z);
    }

    private int getBurnTimeFromItem(ItemStack itemStack) {
        if (itemStack == null)
            return 0;
        return LookupFuelFurnaceBlast.instance.getFuelYield((itemStack.getItem()).id);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        if (this.worldObj.getBlockTileEntity(this.x, this.y, this.z) != this) {
            return false;
        } else {
            return entityplayer.distanceToSqr((double)this.x + 0.5, (double)this.y + 0.5, (double)this.z + 0.5) <= 64.0;
        }
    }

    @Override
    public void sortInventory() {
    }
}
