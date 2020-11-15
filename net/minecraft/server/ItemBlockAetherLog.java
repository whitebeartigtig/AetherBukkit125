package net.minecraft.server;

import forge.*;

public class ItemBlockAetherLog extends ItemBlock implements ITextureProvider
{
    public String getTextureFile() {
        return "/aetherBlocks.png";
    }
    
    public ItemBlockAetherLog(final int itemID) {
        super(itemID);
        this.a("BlockAetherLog");
        this.a(true);
    }
    
    public String getItemNameIS(final ItemStack itemstack) {
        int i = itemstack.getData();
        if (i > 2) {
            i = 2;
        }
        if (i == 1) {
            i = 0;
        }
        return this.getName() + i;
    }
    
    public int getMetadata(final int damage) {
        if (damage <= 1) {
            return 1;
        }
        return 3;
    }
}
