package kodo777.btatech.tileentity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import kodo777.btatech.BtATech;
import kodo777.btatech.block.BlockSteamPressingHammer;
import kodo777.btatech.recipe.LookupFuelSteamPressingHammer;
import kodo777.btatech.recipe.RecipesSteamPressingHammer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.crafting.LookupFuelFurnaceBlast;
import net.minecraft.core.player.inventory.IInventory;

public class TileEntitySteamPressingHammer extends TileEntity implements IInventory{
    private ItemStack[] steamPressingHammerItemStacks = new ItemStack[5];
    public int maxBurnTime = 0;
    public int currentCookTime = 0;
    public int maxCookTime = 200;
    public int currentBurnTime = 0;
    public TileEntitySteamPressingHammer(){
    }

    @Override
    public int getSizeInventory() {
        return this.steamPressingHammerItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.steamPressingHammerItemStacks[i];
    }

    @Override
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

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.steamPressingHammerItemStacks[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

    }

    @Override
    public String getInvName() {
        return "Steam Pressing Hammer";
    }

    @Override
    public void readFromNBT(CompoundTag CompoundTag) {
        super.readFromNBT(CompoundTag);
        ListTag ListTag = CompoundTag.getList("Items");
        this.steamPressingHammerItemStacks = new ItemStack[this.getSizeInventory()];

        for(int i = 0; i < ListTag.tagCount(); ++i) {
            CompoundTag CompoundTag1 = (CompoundTag)ListTag.tagAt(i);
            byte byte0 = CompoundTag1.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.steamPressingHammerItemStacks.length) {
                this.steamPressingHammerItemStacks[byte0] = ItemStack.readItemStackFromNbt(CompoundTag1);
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

        for(int i = 0; i < this.steamPressingHammerItemStacks.length; ++i) {
            if (this.steamPressingHammerItemStacks[i] != null) {
                CompoundTag CompoundTag1 = new CompoundTag();
                CompoundTag1.putByte("Slot", (byte)i);
                this.steamPressingHammerItemStacks[i].writeToNBT(CompoundTag1);
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
        boolean steamPressingHammerUpdated = false;
        if (this.currentBurnTime > 0) {
            --this.currentBurnTime;
        }

        if (!this.worldObj.isClientSide) {
            if (this.worldObj.getBlockId(this.x, this.y, this.z) == BtATech.steamPressingHammerIdle.id && this.currentBurnTime == 0 && this.steamPressingHammerItemStacks[0] == null && this.steamPressingHammerItemStacks[1] != null && this.steamPressingHammerItemStacks[1].itemID == Block.netherrack.id) {
                --this.steamPressingHammerItemStacks[4].stackSize;
                if (this.steamPressingHammerItemStacks[4].stackSize == 0) {
                    this.steamPressingHammerItemStacks[4] = null;
                }

                BlockSteamPressingHammer.updateSteamPressingHammerBlockState(true, this.worldObj, this.x, this.y, this.z);
                steamPressingHammerUpdated = true;
            }

            if (this.currentBurnTime == 0 && this.canSmelt()) {
                this.maxBurnTime = this.currentBurnTime = this.getBurnTimeFromItem(this.steamPressingHammerItemStacks[1]);
                if (this.currentBurnTime > 0) {
                    steamPressingHammerUpdated = true;
                    if (this.steamPressingHammerItemStacks[1] != null) {
                        if (this.steamPressingHammerItemStacks[1].getItem() == BtATech.bucketSteam) {
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
            ItemStack itemstack = RecipesSteamPressingHammer.smelting().getSmeltingResult(this.steamPressingHammerItemStacks[0].getItem().id);
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
            ItemStack itemstack = RecipesSteamPressingHammer.smelting().getSmeltingResult(this.steamPressingHammerItemStacks[0].getItem().id);
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
        BlockSteamPressingHammer.updateSteamPressingHammerBlockState(this.currentBurnTime > 0, this.worldObj, this.x, this.y, this.z);
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
