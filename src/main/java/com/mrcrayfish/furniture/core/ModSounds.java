package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Author: MrCrayfish
 */
public class ModSounds
{
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MOD_ID);

    public static final RegistryObject<SoundEvent> BLOCK_CABINET_OPEN = register("block.cabinet.open");
    public static final RegistryObject<SoundEvent> BLOCK_CABINET_CLOSE = register("block.cabinet.close");
    public static final RegistryObject<SoundEvent> BLOCK_BEDSIDE_CABINET_OPEN = register("block.bedside_cabinet.open");
    public static final RegistryObject<SoundEvent> BLOCK_BEDSIDE_CABINET_CLOSE = register("block.bedside_cabinet.close");
    public static final RegistryObject<SoundEvent> BLOCK_BLINDS_OPEN = register("block.blinds.open");
    public static final RegistryObject<SoundEvent> BLOCK_BLINDS_CLOSE = register("block.blinds.close");
    public static final RegistryObject<SoundEvent> BLOCK_TRAMPOLINE_BOUNCE = register("block.trampoline.bounce");
    public static final RegistryObject<SoundEvent> BLOCK_GRILL_PLACE = register("block.grill.place");
    public static final RegistryObject<SoundEvent> BLOCK_GRILL_FLIP = register("block.grill.flip");
    public static final RegistryObject<SoundEvent> BLOCK_DIVING_BOARD_BOUNCE = register("block.diving_board.bounce");
    public static final RegistryObject<SoundEvent> BLOCK_FRIDGE_OPEN = register("block.fridge.open");
    public static final RegistryObject<SoundEvent> BLOCK_FRIDGE_CLOSE = register("block.fridge.close");

    private static RegistryObject<SoundEvent> register(String name)
    {
        return REGISTER.register(name, () -> new SoundEvent(new ResourceLocation(Reference.MOD_ID, name)));
    }
}
