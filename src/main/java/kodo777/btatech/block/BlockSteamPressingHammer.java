package kodo777.btatech.block;

import kodo777.btatech.BtATech;
import kodo777.btatech.gui.GuiSteamBoiler;
import kodo777.btatech.gui.GuiSteamPressingHammer;
import kodo777.btatech.interfaces.IEntityPlayer;
import kodo777.btatech.interfaces.IEntityPlayerSP;
import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamPressingHammer;
import net.minecraft.src.*;

import java.util.Random;

public class BlockSteamPressingHammer extends BlockContainerRotatable {
    protected Random steamPressingHammerRand = new Random();
    protected final boolean isActive;
    protected static boolean keepSteamPressingHammerInventory = false;

    public BlockSteamPressingHammer(int i, Boolean flag){ super(i, Material.rock); this.isActive=flag;}
    public int idDropped(int i, Random random) {
        return BtATech.steamPressingHammerIdle.blockID;
    }
    protected TileEntity getBlockEntity() {return new TileEntitySteamPressingHammer();}
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        if (this.isActive) {
            int l = world.getBlockMetadata(i, j, k);
            float f = (float)i + 0.5F;
            float f1 = (float)j + 0.0F + random.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)k + 0.5F;
            float f3 = 0.52F;
            float f4 = random.nextFloat() * 0.6F - 0.3F;
            if (l == 4) {
                world.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0);
            } else if (l == 5) {
                world.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0);
            } else if (l == 2) {
                world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0, 0.0, 0.0);
            } else if (l == 3) {
                world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0, 0.0, 0.0);
            }

        }
    }
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        TileEntitySteamPressingHammer tileentitysteampressinghammer = (TileEntitySteamPressingHammer) world.getBlockTileEntity(i, j, k);
        if (tileentitysteampressinghammer != null){
            entityplayer.displayGui(new GuiSteamPressingHammer(entityplayer.inventory, tileentitysteampressinghammer));
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
                world.setBlockWithNotify(x, y, z, BtATech.steamPressingHammerActive.blockID);
            } else {
                world.setBlockWithNotify(x, y, z, BtATech.steamPressingHammerIdle.blockID);
            }

            keepSteamPressingHammerInventory = false;
            world.setBlockMetadataWithNotify(x, y, z, l);
            tileentity.validate();
            world.setBlockTileEntity(x, y, z, tileentity);
        }
    }
    public void onBlockRemoval(World world, int i, int j, int k) {
        if (!keepSteamPressingHammerInventory) {
            TileEntitySteamPressingHammer tileentitysteampressinghammer = (TileEntitySteamPressingHammer) world.getBlockTileEntity(i, j, k);

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
                        EntityItem entityitem = new EntityItem(world, (double)((float)i + f), (double)((float)j + f1), (double)((float)k + f2), new ItemStack(itemstack.itemID, i1, itemstack.getMetadata()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.steamPressingHammerRand.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.steamPressingHammerRand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.steamPressingHammerRand.nextGaussian() * f3);
                        world.entityJoinedWorld(entityitem);
                    }
                }
            }
        }

        super.onBlockRemoval(world, i, j, k);
    }
}
