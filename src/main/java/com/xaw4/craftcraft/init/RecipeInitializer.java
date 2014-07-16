package com.xaw4.craftcraft.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

/**
 * Created by Xaw4 on 15.07.2014.
 */
public class RecipeInitializer {

    public static void registerRecipes()
    {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.slotChest,4),
            " c ",
            "chc",
            " c ", 'c', Blocks.chest, 'h', Blocks.hopper);
    }
}
