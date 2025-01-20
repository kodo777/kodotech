package kodo777.btatech.tileentity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import kodo777.btatech.BtATech;
import kodo777.btatech.block.BlockSteamForge;
import kodo777.btatech.recipe.LookupFuelSteamPressingHammer;
import kodo777.btatech.recipe.RecipesSteamForge;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.crafting.LookupFuelFurnaceBlast;
import net.minecraft.core.player.inventory.IInventory;

public class TileEntitySteamForge extends TileEntity implements IInventory{
    private ItemStack[] steamForgeItemStacks = new ItemStack[5];
    public int maxBurnTime = 0;
    public int currentCookTime = 0;
    public int maxCookTime = 200;
    public int currentBurnTime = 0;
    public TileEntitySteamForge(){
    }
    
    @Override
    public int getSizeInventory() {
        return this.steamForgeItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.steamForgeItemStacks[i];
    }

    @Override
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

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.steamForgeItemStacks[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

    }

    @Override
    public String getInvName() {
        return "Steam Forge";
    }

    @Override
    public void readFromNBT(CompoundTag CompoundTag) {
        super.readFromNBT(CompoundTag);
        ListTag ListTag = CompoundTag.getList("Items");
        this.steamForgeItemStacks = new ItemStack[this.getSizeInventory()];

        for(int i = 0; i < ListTag.tagCount(); ++i) {
            CompoundTag CompoundTag1 = (CompoundTag)ListTag.tagAt(i);
            byte byte0 = CompoundTag1.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.steamForgeItemStacks.length) {
                this.steamForgeItemStacks[byte0] = ItemStack.readItemStackFromNbt(CompoundTag1);
            }
        }

        this.currentBurnTime = CompoundTag.getShort("BurnTime");
        this.currentCookTime = CompoundTag.getShort("CookTime");
        this.maxBurnTime = CompoundTag.getShort("MaxBurnTime");
    }

    @Override
    public void writeToNBT(CompoundTag CompoundTag) {
        super.writeToNBT(CompoundTag);
        CompoundTag.putShort("BurnTime", (short)this.currentBurnTime);
        CompoundTag.putShort("CookTime", (short)this.currentCookTime);
        CompoundTag.putShort("MaxBurnTime", (short)this.maxBurnTime);
        ListTag ListTag = new ListTag();

        for(int i = 0; i < this.steamForgeItemStacks.length; ++i) {
            if (this.steamForgeItemStacks[i] != null) {
                CompoundTag CompoundTag1 = new CompoundTag();
                CompoundTag1.putByte("Slot", (byte)i);
                this.steamForgeItemStacks[i].writeToNBT(CompoundTag1);
                ListTag.addTag(CompoundTag1);
            }
        }

        CompoundTag.put("Items", ListTag);
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
        boolean steamForgeUpdated = false;
        if (this.currentBurnTime > 0) {
            --this.currentBurnTime;
        }

        if (!this.worldObj.isClientSide) {
            if (this.worldObj.getBlockId(this.x, this.y, this.z) == BtATech.steamForgeIdle.id && this.currentBurnTime == 0 && this.steamForgeItemStacks[0] == null && this.steamForgeItemStacks[1] != null && this.steamForgeItemStacks[1].itemID == Block.netherrack.id) {
                --this.steamForgeItemStacks[4].stackSize;
                if (this.steamForgeItemStacks[4].stackSize == 0) {
                    this.steamForgeItemStacks[4] = null;
                }

                BlockSteamForge.updateSteamForgeBlockState(true, this.worldObj, this.x, this.y, this.z);
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
            ItemStack itemstack = RecipesSteamForge.smelting().getSmeltingResult(this.steamForgeItemStacks[0].getItem().id);
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
            ItemStack itemstack = RecipesSteamForge.smelting().getSmeltingResult(this.steamForgeItemStacks[0].getItem().id);
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
        BlockSteamForge.updateSteamForgeBlockState(this.currentBurnTime > 0, this.worldObj, this.x, this.y, this.z);
    }

    private int getBurnTimeFromItem(ItemStack itemStack) {
        return itemStack == null ? 0 : LookupFuelSteamPressingHammer.fuelSteamPressingHammer().getFuelYield(itemStack.getItem().id);
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
