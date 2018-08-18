package com.mrcrayfish.furniture.blocks;

/**
 * Author: MrCrayfish
 */
public class BlockCouchJeb extends BlockCouch
{
    public BlockCouchJeb()
    {
    	super("couch_jeb");
        this.setCreativeTab(null);
    }

    @Override
    public boolean isSpecial()
    {
        return true;
    }
}
