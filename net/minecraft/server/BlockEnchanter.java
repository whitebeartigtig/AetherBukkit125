package net.minecraft.server;

import forge.*;
import java.util.*;

public class BlockEnchanter extends BlockContainer implements ITextureProvider
{
    private Random EnchanterRand;
    private int sideTexture;
    
    public String getTextureFile() {
        return "/aetherBlocks.png";
    }
    
    protected BlockEnchanter(final int blockID) {
        super(blockID, Material.STONE);
        ((Block)this).textureId = 8;
        this.sideTexture = 7;
        this.EnchanterRand = new Random();
        this.c(2.0f);
    }
    
    public void onBlockAdded(final World world, final int i, final int j, final int k) {
        super.onPlace(world, i, j, k);
        this.setDefaultDirection(world, i, j, k);
    }
    
    private void setDefaultDirection(final World world, final int i, final int j, final int k) {
        if (world.isStatic) {
            return;
        }
        final int l = world.getTypeId(i, j, k - 1);
        final int i2 = world.getTypeId(i, j, k + 1);
        final int j2 = world.getTypeId(i - 1, j, k);
        final int k2 = world.getTypeId(i + 1, j, k);
        byte byte0 = 3;
        if (Block.n[l] && !Block.n[i2]) {
            byte0 = 3;
        }
        if (Block.n[i2] && !Block.n[l]) {
            byte0 = 2;
        }
        if (Block.n[j2] && !Block.n[k2]) {
            byte0 = 5;
        }
        if (Block.n[k2] && !Block.n[j2]) {
            byte0 = 4;
        }
        world.setData(i, j, k, (int)byte0);
    }
    
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        final TileEntityEnchanter tileentity = (TileEntityEnchanter)world.getTileEntity(i, j, k);
        if (tileentity.isBurning()) {
            final float f = i + 0.5f;
            final float f2 = j + 1.0f + random.nextFloat() * 6.0f / 16.0f;
            final float f3 = k + 0.5f;
            world.a("smoke", (double)f, (double)f2, (double)f3, 0.0, 0.0, 0.0);
            world.a("flame", (double)f, (double)f2, (double)f3, 0.0, 0.0, 0.0);
        }
    }
    
    public int getBlockTextureFromSide(final int i) {
        if (i == 1) {
            return ((Block)this).id;
        }
        if (i == 0) {
            return ((Block)this).id;
        }
        return this.sideTexture;
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityhuman) {
        final TileEntityEnchanter tileentityenchanter = (TileEntityEnchanter)world.getTileEntity(i, j, k);
        ModLoader.openGUI(entityhuman, 80, (IInventory)tileentityenchanter, (Container)new ContainerEnchanter(entityhuman.inventory, tileentityenchanter));
        return true;
    }
    
    public static void updateEnchanterBlockState(final boolean flag, final World world, final int i, final int j, final int k) {
        final int l = world.getData(i, j, k);
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        world.setData(i, j, k, l);
        world.setTileEntity(i, j, k, tileentity);
    }
    
    public TileEntity getBlockEntity() {
        return new TileEntityEnchanter();
    }
    
    public void onBlockPlacedBy(final World world, final int i, final int j, final int k, final EntityLiving entityliving) {
        final int l = MathHelper.floor(((Entity)entityliving).yaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (l == 0) {
            world.setData(i, j, k, 2);
        }
        if (l == 1) {
            world.setData(i, j, k, 5);
        }
        if (l == 2) {
            world.setData(i, j, k, 3);
        }
        if (l == 3) {
            world.setData(i, j, k, 4);
        }
    }
    
    public void onBlockRemoval(final World world, final int i, final int j, final int k) {
        final TileEntityEnchanter tileentityEnchanter = (TileEntityEnchanter)world.getTileEntity(i, j, k);
        for (int l = 0; l < tileentityEnchanter.getSizeInventory(); ++l) {
            final ItemStack itemstack = tileentityEnchanter.getStackInSlot(l);
            if (itemstack != null) {
                final float f = this.EnchanterRand.nextFloat() * 0.8f + 0.1f;
                final float f2 = this.EnchanterRand.nextFloat() * 0.8f + 0.1f;
                final float f3 = this.EnchanterRand.nextFloat() * 0.8f + 0.1f;
                while (itemstack.count > 0) {
                    int i2 = this.EnchanterRand.nextInt(21) + 10;
                    if (i2 > itemstack.count) {
                        i2 = itemstack.count;
                    }
                    final ItemStack itemStack = itemstack;
                    itemStack.count -= i2;
                    final EntityItem entityitem = new EntityItem(world, (double)(i + f), (double)(j + f2), (double)(k + f3), new ItemStack(itemstack.id, i2, itemstack.getData()));
                    final float f4 = 0.05f;
                    ((Entity)entityitem).motX = (float)this.EnchanterRand.nextGaussian() * f4;
                    ((Entity)entityitem).motY = (float)this.EnchanterRand.nextGaussian() * f4 + 0.2f;
                    ((Entity)entityitem).motZ = (float)this.EnchanterRand.nextGaussian() * f4;
                    world.addEntity((Entity)entityitem);
                }
            }
        }
        super.remove(world, i, j, k);
    }
    
    public void addCreativeItems(final ArrayList itemList) {
        itemList.add(new ItemStack((Block)this));
    }

	@Override
	public TileEntity a_() {
		// TODO Auto-generated method stub
		return null;
	}
}
