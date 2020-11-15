package net.minecraft.server;

import forge.*;

public class ItemBlockTrap extends ItemBlock implements ITextureProvider
{
    public String getTextureFile() {
        return "/aetherBlocks.png";
    }
    
    public ItemBlockTrap(final int i) {
        super(i);
        this.a(true);
    }
    
    public int getMetadata(final int damage) {
        return damage;
    }
    
    public String getItemNameIS(final ItemStack itemstack) {
        int i = itemstack.getData();
        if (i > 2) {
            i = 2;
        }
        return this.getName() + i;
    }
}
