package com.xaw4.craftcraft.creativetab;

import com.xaw4.craftcraft.constants.ModProperties;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Created by Xaw4 on 14.07.2014.
 */
public class CraftCraftCreativeTab extends CreativeTabs {

    public static final CraftCraftCreativeTab instance = new CraftCraftCreativeTab();

    public CraftCraftCreativeTab() {
        super(ModProperties.MOD_ID);
    }

    @Override
    public Item getTabIconItem() {
        return Items.stick;
    }

    @Override
    public String getTranslatedTabLabel(){
        // TODO
        return "CraftCraft";
    }
}
