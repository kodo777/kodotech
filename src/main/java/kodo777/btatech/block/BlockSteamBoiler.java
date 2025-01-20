package kodo777.btatech.block;

import kodo777.btatech.BtATech;
import kodo777.btatech.KodoGui;
import kodo777.btatech.gui.GuiSteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockSteamBoiler extends BlockTileEntityRotatable {
    protected Random steamBoilerRand = new Random();
    protected final boolean isActive;
    protected static boolean keepSteamBoilerInventory = false;

    public BlockSteamBoiler(String key, int i, Boolean flag){
        super(key, i, Material.stone);
        this.isActive = flag;
    }
    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        return new ItemStack[] { new ItemStack(BtATech.steamBoilerIdle) };
    }
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        if (this.isActive) {
            int l = world.getBlockMetadata(i, j, k);
            float f = (float)i + 0.5F;
            float f1 = (float)j + 0.0F + random.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)k + 0.5F;
            float f3 = 0.52F;
            float f4 = random.nextFloat() * 0.6F - 0.3F;
            if (l == 4) {
                world.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0, 0);
                world.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0, 0);
            } else if (l == 5) {
                world.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0, 0);
                world.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0, 0);
            } else if (l == 2) {
                world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0, 0.0, 0.0, 0);
                world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0, 0.0, 0.0, 0);
            } else if (l == 3) {
                world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0, 0.0, 0.0, 0);
                world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0, 0.0, 0.0, 0);
            }

        }
    }
    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xPlaced, double yPlaced) {
        TileEntitySteamBoiler tileentitysteamboiler = (TileEntitySteamBoiler) world.getBlockTileEntity(x, y, z);
        if (tileentitysteamboiler != null){
            ((KodoGui) player).kodotech$displayGuiSteamBoiler(tileentitysteamboiler);
        }
        return true;
    }
    public static void updateSteamBoilerBlockState(boolean lit, World world, int x, int y, int z) {
        int l = world.getBlockMetadata(x, y, z);
        TileEntity tileentity = world.getBlockTileEntity(x, y, z);
        if (tileentity == null) {
            world.setBlockWithNotify(x, y, z, 0);
        } else {
            keepSteamBoilerInventory = true;
            if (lit) {
                world.setBlockWithNotify(x, y, z, BtATech.steamBoilerActive.id);
            } else {
                world.setBlockWithNotify(x, y, z, BtATech.steamBoilerIdle.id);
            }

            keepSteamBoilerInventory = false;
            world.setBlockMetadataWithNotify(x, y, z, l);
            tileentity.validate();
            world.setBlockTileEntity(x, y, z, tileentity);
        }
    }

    @Override
    public void onBlockRemoved(World world, int x, int y, int z, int data) {
        if (!keepSteamBoilerInventory) {
            TileEntitySteamBoiler tileentitysteamboiler = (TileEntitySteamBoiler)world.getBlockTileEntity(x, y, z);

            for(int l = 0; l < tileentitysteamboiler.getSizeInventory(); ++l) {
                ItemStack itemstack = tileentitysteamboiler.getStackInSlot(l);
                if (itemstack != null) {
                    float f = this.steamBoilerRand.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.steamBoilerRand.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.steamBoilerRand.nextFloat() * 0.8F + 0.1F;

                    while(itemstack.stackSize > 0) {
                        int i1 = this.steamBoilerRand.nextInt(21) + 10;
                        if (i1 > itemstack.stackSize) {
                            i1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= i1;
                        EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.itemID, i1, itemstack.getMetadata()));
                        float f3 = 0.05F;
                        entityitem.xd = (double)((float)this.steamBoilerRand.nextGaussian() * f3);
                        entityitem.yd = (double)((float)this.steamBoilerRand.nextGaussian() * f3 + 0.2F);
                        entityitem.zd = (double)((float)this.steamBoilerRand.nextGaussian() * f3);
                        world.entityJoinedWorld(entityitem);
                    }
                }
            }
        }

        super.onBlockRemoved(world, x, y, z, data);
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntitySteamBoiler();
    }
}
