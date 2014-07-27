package com.xaw4.craftcraft.init;

import com.xaw4.craftcraft.general.BlockCC;
import com.xaw4.craftcraft.slotchest.BlockSlotChest;
import com.xaw4.craftcraft.slotcrafter.BlockSlotCrafter;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by Xaw4 on 14.07.2014.
 */
public class ModBlocks {
    public static final BlockCC slotChest = new BlockSlotChest();
    public static final BlockCC slotCrafter = new BlockSlotCrafter();


    public static void init(){
        slotChest.registerBlock();
        slotCrafter.registerBlock();
    }

}
