package net.minecraft.server;

import forge.*;

public class ItemBlockBlueLeaves extends ItemBlock implements ITextureProvider
{
    public String getTextureFile() {
        return "/aetherBlocks.png";
    }
    
    public ItemBlockBlueLeaves(final int i) {
        super(i);
        this.a(true);
    }
    
    public int getMetadata(final int damage) {
        return damage;
    }
    
    public String getItemNameIS(final ItemStack itemstack) {
        int i = itemstack.getData();
        if (i > 1) {
            i = 1;
        }
        return this.getName() + i;
    }
}
