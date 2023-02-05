package kodo777.btatech.tileentity;

import kodo777.btatech.BtATech;
import kodo777.btatech.block.BlockSteamForge;
import kodo777.btatech.recipe.LookupFuelSteamPressingHammer;
import kodo777.btatech.recipe.RecipesSteamForge;
import net.minecraft.src.*;

public class TileEntitySteamForge extends TileEntity implements IInventory{
    private ItemStack[] steamForgeItemStacks = new ItemStack[5];
    public int maxBurnTime = 0;
    public int currentCookTime = 0;
    public int maxCookTime = 200;
    public int currentBurnTime = 0;
    public TileEntitySteamForge(){

    }
    public int getSizeInventory() {
        return this.steamForgeItemStacks.length;
    }

    public ItemStack getStackInSlot(int i) {
        return this.steamForgeItemStacks[i];
    }

    public ItemStack decrStackSize(int i, int j) {
        if (this.steamForgeItemStacks[i] != null) {
            ItemStack itemstack1;
            if (this.steamForgeItemStacks[i].stackSize <= j) {
                itemstack1 = this.steamForgeItemStacks[i];
                this.steamForgeItemStacks[i] = null;
                return itemstack1;
            } else {
                itemstack1 = this.steamForgeItemStacks[i].splitStack(j);
                if (this.steamForgeItemStacks[i].stackSize == 0) {
                    this.steamForgeItemStacks[i] = null;
                }

                return itemstack1;
            }
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.steamForgeItemStacks[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

    }

    public String getInvName() {
        return "Steam Forge";
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        this.steamForgeItemStacks = new ItemStack[this.getSizeInventory()];

        for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.steamForgeItemStacks.length) {
                this.steamForgeItemStacks[byte0] = new ItemStack(nbttagcompound1);
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

        for(int i = 0; i < this.steamForgeItemStacks.length; ++i) {
            if (this.steamForgeItemStacks[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.steamForgeItemStacks[i].writeToNBT(nbttagcompound1);
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
        boolean steamForgeUpdated = false;
        if (this.currentBurnTime > 0) {
            --this.currentBurnTime;
        }

        if (!this.worldObj.isMultiplayerAndNotHost) {
            if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == BtATech.steamForgeIdle.blockID && this.currentBurnTime == 0 && this.steamForgeItemStacks[0] == null && this.steamForgeItemStacks[1] != null && this.steamForgeItemStacks[1].itemID == Block.netherrack.blockID) {
                --this.steamForgeItemStacks[4].stackSize;
                if (this.steamForgeItemStacks[4].stackSize == 0) {
                    this.steamForgeItemStacks[4] = null;
                }

                BlockSteamForge.updateSteamForgeBlockState(true, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
                steamForgeUpdated = true;
            }

            if (this.currentBurnTime == 0 && this.canSmelt()) {
                this.maxBurnTime = this.currentBurnTime = this.getBurnTimeFromItem(this.steamForgeItemStacks[1]);
                if (this.currentBurnTime > 0) {
                    steamForgeUpdated = true;
                    if (this.steamForgeItemStacks[1] != null) {
                        if (this.steamForgeItemStacks[1].getItem() == BtATech.bucketSteam) {
                            this.steamForgeItemStacks[1] = new ItemStack(Item.bucket);
                        } else {
                            --this.steamForgeItemStacks[1].stackSize;
                            if (this.steamForgeItemStacks[1].stackSize == 0) {
                                this.steamForgeItemStacks[1] = null;
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
                    steamForgeUpdated = true;
                }
            } else {
                this.currentCookTime = 0;
            }

            if (isBurnTimeHigherThan0 != this.currentBurnTime > 0) {
                steamForgeUpdated = true;
                this.updateSteamForge();
            }
        }

        if (steamForgeUpdated) {
            this.onInventoryChanged();
        }

    }

    private boolean canSmelt() {
        if (steamForgeItemStacks[0] == null || steamForgeItemStacks[3] == null || steamForgeItemStacks[4] == null) {
            return false;
        } else {
            ItemStack itemstack = RecipesSteamForge.smelting().getSmeltingResult(this.steamForgeItemStacks[0].getItem().itemID);
            if (itemstack == null) {
                return false;
            } else if (this.steamForgeItemStacks[2] == null) {
                return true;
            } else if (!this.steamForgeItemStacks[2].isItemEqual(itemstack)) {
                return false;
            } else if (this.steamForgeItemStacks[2].stackSize < this.getInventoryStackLimit() && this.steamForgeItemStacks[2].stackSize < this.steamForgeItemStacks[2].getMaxStackSize()) {
                return true;
            } else {
                return this.steamForgeItemStacks[2].stackSize < itemstack.getMaxStackSize();
            }
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemstack = RecipesSteamForge.smelting().getSmeltingResult(this.steamForgeItemStacks[0].getItem().itemID);
            if (this.steamForgeItemStacks[2] == null) {
                this.steamForgeItemStacks[2] = itemstack.copy();
            } else if (this.steamForgeItemStacks[2].itemID == itemstack.itemID) {
                ++this.steamForgeItemStacks[2].stackSize;
            }

            --this.steamForgeItemStacks[0].stackSize;
            --this.steamForgeItemStacks[3].stackSize;
            --this.steamForgeItemStacks[4].stackSize;
            if (this.steamForgeItemStacks[0].stackSize <= 0) {
                this.steamForgeItemStacks[0] = null;
            }
            if (this.steamForgeItemStacks[3].stackSize <= 0) {
                this.steamForgeItemStacks[3] = null;
            }
            if (this.steamForgeItemStacks[4].stackSize <= 0) {
                this.steamForgeItemStacks[4] = null;
            }
        }
    }

    protected void updateSteamForge() {
        BlockSteamForge.updateSteamForgeBlockState(this.currentBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    private int getBurnTimeFromItem(ItemStack itemStack) {
        return itemStack == null ? 0 : LookupFuelSteamPressingHammer.fuelSteamPressingHammer().getFuelYield(itemStack.getItem().itemID);
    }

    public boolean canInteractWith(EntityPlayer entityplayer) {
        if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
            return false;
        } else {
            return entityplayer.getDistanceSq((double)this.xCoord + 0.5, (double)this.yCoord + 0.5, (double)this.zCoord + 0.5) <= 64.0;
        }
    }
}
