package kodo777.btatech.tileentity;

import kodo777.btatech.BtATech;
import kodo777.btatech.block.BlockSteamPressingHammer;
import kodo777.btatech.recipe.RecipesSteamPressingHammer;
import net.minecraft.src.*;

public class TileEntitySteamPressingHammer extends TileEntity implements IInventory{
    private ItemStack[] steamPressingHammerItemStacks = new ItemStack[5];
    public int maxBurnTime = 0;
    public int currentCookTime = 0;
    public int maxCookTime = 200;
    public int currentBurnTime = 0;
    public TileEntitySteamPressingHammer(){

    }
    public int getSizeInventory() {
        return this.steamPressingHammerItemStacks.length;
    }

    public ItemStack getStackInSlot(int i) {
        return this.steamPressingHammerItemStacks[i];
    }

    public ItemStack decrStackSize(int i, int j) {
        if (this.steamPressingHammerItemStacks[i] != null) {
            ItemStack itemstack1;
            if (this.steamPressingHammerItemStacks[i].stackSize <= j) {
                itemstack1 = this.steamPressingHammerItemStacks[i];
                this.steamPressingHammerItemStacks[i] = null;
                return itemstack1;
            } else {
                itemstack1 = this.steamPressingHammerItemStacks[i].splitStack(j);
                if (this.steamPressingHammerItemStacks[i].stackSize == 0) {
                    this.steamPressingHammerItemStacks[i] = null;
                }

                return itemstack1;
            }
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.steamPressingHammerItemStacks[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

    }

    public String getInvName() {
        return "Steam Pressing Hammer";
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        this.steamPressingHammerItemStacks = new ItemStack[this.getSizeInventory()];

        for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.steamPressingHammerItemStacks.length) {
                this.steamPressingHammerItemStacks[byte0] = new ItemStack(nbttagcompound1);
            }
        }

        this.currentBurnTime = nbttagcompound.getShort("BurnTime");
        this.currentCookTime = nbttagcompound.getShort("CookTime");
        this.maxBurnTime = nbttagcompound.getShort("MaxBurnTime");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("BurnTime", (short)this.currentBurnTime);
        nbttagcompound.setShort("CookTime", (short)this.currentCookTime);
        nbttagcompound.setShort("MaxBurnTime", (short)this.maxBurnTime);
        NBTTagList nbttaglist = new NBTTagList();

        for(int i = 0; i < this.steamPressingHammerItemStacks.length; ++i) {
            if (this.steamPressingHammerItemStacks[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.steamPressingHammerItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.setTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

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

    public void updateEntity() {
        boolean isBurnTimeHigherThan0 = this.currentBurnTime > 0;
        boolean steamPressingHammerUpdated = false;
        if (this.currentBurnTime > 0) {
            --this.currentBurnTime;
        }

        if (!this.worldObj.isMultiplayerAndNotHost) {
            if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == BtATech.steamPressingHammerIdle.blockID && this.currentBurnTime == 0 && this.steamPressingHammerItemStacks[0] == null && this.steamPressingHammerItemStacks[1] != null && this.steamPressingHammerItemStacks[1].itemID == Block.netherrack.blockID) {
                --this.steamPressingHammerItemStacks[4].stackSize;
                if (this.steamPressingHammerItemStacks[4].stackSize == 0) {
                    this.steamPressingHammerItemStacks[4] = null;
                }

                BlockSteamPressingHammer.updateSteamPressingHammerBlockState(true, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
                steamPressingHammerUpdated = true;
            }

            if (this.currentBurnTime == 0 && this.canSmelt()) {
                this.maxBurnTime = this.currentBurnTime = this.getBurnTimeFromItem(this.steamPressingHammerItemStacks[1]);
                if (this.currentBurnTime > 0) {
                    steamPressingHammerUpdated = true;
                    if (this.steamPressingHammerItemStacks[1] != null) {
                        if (this.steamPressingHammerItemStacks[1].getItem() == Item.bucketLava) {
                            this.steamPressingHammerItemStacks[1] = new ItemStack(Item.bucket);
                        } else {
                            --this.steamPressingHammerItemStacks[1].stackSize;
                            if (this.steamPressingHammerItemStacks[1].stackSize == 0) {
                                this.steamPressingHammerItemStacks[1] = null;
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
                    steamPressingHammerUpdated = true;
                }
            } else {
                this.currentCookTime = 0;
            }

            if (isBurnTimeHigherThan0 != this.currentBurnTime > 0) {
                steamPressingHammerUpdated = true;
                this.updateSteamPressingHammer();
            }
        }

        if (steamPressingHammerUpdated) {
            this.onInventoryChanged();
        }

    }

    private boolean canSmelt() {
        if (steamPressingHammerItemStacks[0] == null || steamPressingHammerItemStacks[3] == null || steamPressingHammerItemStacks[4] == null) {
            return false;
        } else {
            ItemStack itemstack = RecipesSteamPressingHammer.smelting().getSmeltingResult(this.steamPressingHammerItemStacks[0].getItem().itemID);
            if (itemstack == null) {
                return false;
            } else if (this.steamPressingHammerItemStacks[2] == null) {
                return true;
            } else if (!this.steamPressingHammerItemStacks[2].isItemEqual(itemstack)) {
                return false;
            } else if (this.steamPressingHammerItemStacks[2].stackSize < this.getInventoryStackLimit() && this.steamPressingHammerItemStacks[2].stackSize < this.steamPressingHammerItemStacks[2].getMaxStackSize()) {
                return true;
            } else {
                return this.steamPressingHammerItemStacks[2].stackSize < itemstack.getMaxStackSize();
            }
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemstack = RecipesSteamPressingHammer.smelting().getSmeltingResult(this.steamPressingHammerItemStacks[0].getItem().itemID);
            if (this.steamPressingHammerItemStacks[2] == null) {
                this.steamPressingHammerItemStacks[2] = itemstack.copy();
            } else if (this.steamPressingHammerItemStacks[2].itemID == itemstack.itemID) {
                ++this.steamPressingHammerItemStacks[2].stackSize;
            }

            --this.steamPressingHammerItemStacks[0].stackSize;
            --this.steamPressingHammerItemStacks[3].stackSize;
            --this.steamPressingHammerItemStacks[4].stackSize;
            if (this.steamPressingHammerItemStacks[0].stackSize <= 0) {
                this.steamPressingHammerItemStacks[0] = null;
            }
            if (this.steamPressingHammerItemStacks[3].stackSize <= 0) {
                this.steamPressingHammerItemStacks[3] = null;
            }
            if (this.steamPressingHammerItemStacks[4].stackSize <= 0) {
                this.steamPressingHammerItemStacks[4] = null;
            }
        }
    }

    protected void updateSteamPressingHammer() {
        BlockSteamPressingHammer.updateSteamPressingHammerBlockState(this.currentBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    private int getBurnTimeFromItem(ItemStack itemStack) {
        return itemStack == null ? 0 : LookupFuelFurnace.fuelFurnace().getFuelYield(itemStack.getItem().itemID);
    }

    public boolean canInteractWith(EntityPlayer entityplayer) {
        if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
            return false;
        } else {
            return entityplayer.getDistanceSq((double)this.xCoord + 0.5, (double)this.yCoord + 0.5, (double)this.zCoord + 0.5) <= 64.0;
        }
    }
}
