package kodo777.btatech.block;

import kodo777.btatech.BtATech;
import kodo777.btatech.KodoGui;
import kodo777.btatech.gui.GuiSteamPressingHammer;
import kodo777.btatech.tileentity.TileEntitySteamPressingHammer;
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

public class BlockSteamPressingHammer extends BlockTileEntityRotatable {
    protected Random steamPressingHammerRand = new Random();
    protected final boolean isActive;
    protected static boolean keepSteamPressingHammerInventory = false;

    public BlockSteamPressingHammer(String s, int i, Boolean flag){
        super(s, i, Material.stone);
        this.isActive = flag;
    }
    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        return new ItemStack[] { new ItemStack(BtATech.steamPressingHammerIdle) };
    }
    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntitySteamPressingHammer();
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
        TileEntitySteamPressingHammer tileentitysteampressinghammer = (TileEntitySteamPressingHammer) world.getBlockTileEntity(x, y, z);
        if (tileentitysteampressinghammer != null){
            ((KodoGui) player).kodotech$displayGuiSteamPressingHammer(tileentitysteampressinghammer);
        }
        return true;
    }

    public static void updateSteamPressingHammerBlockState(boolean lit, World world, int x, int y, int z) {
        int l = world.getBlockMetadata(x, y, z);
        TileEntity tileentity = world.getBlockTileEntity(x, y, z);
        if (tileentity == null) {
            world.setBlockWithNotify(x, y, z, 0);
        } else {
            keepSteamPressingHammerInventory = true;
            if (lit) {
                world.setBlockWithNotify(x, y, z, BtATech.steamPressingHammerActive.id);
            } else {
                world.setBlockWithNotify(x, y, z, BtATech.steamPressingHammerIdle.id);
            }

            keepSteamPressingHammerInventory = false;
            world.setBlockMetadataWithNotify(x, y, z, l);
            tileentity.validate();
            world.setBlockTileEntity(x, y, z, tileentity);
        }
    }
    @Override
    public void onBlockRemoved(World world, int x, int y, int z, int data) {
        if (!keepSteamPressingHammerInventory) {
            TileEntitySteamPressingHammer tileentitysteampressinghammer = (TileEntitySteamPressingHammer) world.getBlockTileEntity(x, y, z);

            for(int l = 0; l < tileentitysteampressinghammer.getSizeInventory(); ++l) {
                ItemStack itemstack = tileentitysteampressinghammer.getStackInSlot(l);
                if (itemstack != null) {
                    float f = this.steamPressingHammerRand.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.steamPressingHammerRand.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.steamPressingHammerRand.nextFloat() * 0.8F + 0.1F;

                    while(itemstack.stackSize > 0) {
                        int i1 = this.steamPressingHammerRand.nextInt(21) + 10;
                        if (i1 > itemstack.stackSize) {
                            i1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= i1;
                        EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.itemID, i1, itemstack.getMetadata()));
                        float f3 = 0.05F;
                        entityitem.xd = (double)((float)this.steamPressingHammerRand.nextGaussian() * f3);
                        entityitem.yd = (double)((float)this.steamPressingHammerRand.nextGaussian() * f3 + 0.2F);
                        entityitem.zd = (double)((float)this.steamPressingHammerRand.nextGaussian() * f3);
                        world.entityJoinedWorld(entityitem);
                    }
                }
            }
        }

        super.onBlockRemoved(world, x, y, z, data);
    }
}
