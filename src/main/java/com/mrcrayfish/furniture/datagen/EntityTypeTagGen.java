package com.mrcrayfish.furniture.datagen;

import com.mrcrayfish.furniture.Reference;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * @author Ocelot
 */
public class EntityTypeTagGen extends EntityTypeTagsProvider
{
    public EntityTypeTagGen(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, Reference.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags()
    {
    }
}
