package com.xaw4.craftcraft;

import com.xaw4.craftcraft.constants.ModProperties;
import com.xaw4.craftcraft.handler.GuiHandler;
import com.xaw4.craftcraft.handler.NetworkHandler;
import com.xaw4.craftcraft.init.ModBlocks;
import com.xaw4.craftcraft.init.RecipeInitializer;
import com.xaw4.craftcraft.init.TileEntitiesCC;
import com.xaw4.craftcraft.proxy.ClientProxy;
import com.xaw4.craftcraft.proxy.Proxy;
import com.xaw4.craftcraft.util.IconUtil;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
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
    public static CraftCraft instance;

    @SidedProxy(clientSide = ModProperties.CLIENT_PROXY, serverSide = ModProperties.SERVER_PROXY)
    public static Proxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ModBlocks.init();
        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        RecipeInitializer.registerRecipes();
        GuiHandler.init();
        TileEntitiesCC.init();
        NetworkHandler.init();
        IconUtil.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){

    }
}
