package com.xaw4.craftcraft;

import com.xaw4.craftcraft.init.ModBlocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;


/**
 * Created by Xaw4 on 25.06.2014.
 */
@Mod(modid = ModProperties.MOD_ID,
        name = ModProperties.MOD_NAME,
        version = ModProperties.MOD_VERSION)
public class CraftCraft {

    @Mod.Instance(ModProperties.MOD_ID)
    public CraftCraft instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ModBlocks.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){

    }
}
