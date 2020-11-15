package net.minecraft.server;

import forge.*;
import java.util.*;

public class ItemGravititeSpade extends ItemTool implements ITextureProvider
{
    private static Block[] blocksEffectiveAgainst;
    private static Random random;
    
    public String getTextureFile() {
        return "/aetherItems.png";
    }
    
    public ItemGravititeSpade(final int i, final EnumToolMaterial enumtoolmaterial) {
        super(i, 1, enumtoolmaterial, ItemGravititeSpade.blocksEffectiveAgainst);
    }
    
    public boolean canHarvestBlock(final Block block) {
        for (int i = 0; i < ItemGravititeSpade.blocksEffectiveAgainst.length; ++i) {
            if (ItemGravititeSpade.blocksEffectiveAgainst[i].id == block.id) {
                return true;
            }
        }
        return false;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final float f1 = ((Entity)entityplayer).pitch;
        final float f2 = ((Entity)entityplayer).yaw;
        final double d = ((Entity)entityplayer).locX;
        final double d2 = ((Entity)entityplayer).locY + 1.62 - ((Entity)entityplayer).height;
        final double d3 = ((Entity)entityplayer).locZ;
        final Vec3D vec3d = Vec3D.create(d, d2, d3);
        final float f3 = MathHelper.cos(-f2 * 0.01745329f - 3.141593f);
        final float f4 = MathHelper.sin(-f2 * 0.01745329f - 3.141593f);
        final float f5 = -MathHelper.cos(-f1 * 0.01745329f);
        final float f6 = MathHelper.sin(-f1 * 0.01745329f);
        final float f7 = f4 * f5;
        final float f8 = f6;
        final float f9 = f3 * f5;
        final double d4 = 5.0;
        final Vec3D vec3d2 = vec3d.add(f7 * d4, f8 * d4, f9 * d4);
        final MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d2, false);
        if (movingobjectposition == null) {
            return itemstack;
        }
        if (movingobjectposition.type == EnumMovingObjectType.TILE) {
            final int i = movingobjectposition.b;
            final int j = movingobjectposition.c;
            final int k = movingobjectposition.d;
            if (!world.isStatic) {
                int id = world.getTypeId(i, j, k);
                final int metadata = world.getData(i, j, k);
                for (int n = 0; n < ItemGravititeSpade.blocksEffectiveAgainst.length; ++n) {
                    if (id == ItemGravititeSpade.blocksEffectiveAgainst[n].id) {
                        if (id == AetherBlocks.Grass.id) {
                            id = AetherBlocks.Dirt.id;
                        }
                        final EntityFloatingBlock floating = new EntityFloatingBlock(world, i + 0.5f, j + 0.5f, k + 0.5f, id, metadata);
                        world.addEntity((Entity)floating);
                    }
                }
            }
            itemstack.damage(4, (EntityLiving)entityplayer);
        }
        return itemstack;
    }
    
    static {
        ItemGravititeSpade.blocksEffectiveAgainst = new Block[] { AetherBlocks.Quicksoil, AetherBlocks.Dirt, AetherBlocks.Grass, AetherBlocks.Aercloud, Block.SNOW };
        ItemGravititeSpade.random = new Random();
    }
}
