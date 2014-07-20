package com.xaw4.craftcraft.init;

import com.xaw4.craftcraft.general.BlockCC;
import com.xaw4.craftcraft.slotchest.SlotChest;
import com.xaw4.craftcraft.slotcrafter.SlotCrafter;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by Xaw4 on 14.07.2014.
 */
public class ModBlocks {
    public static final BlockCC slotChest = new SlotChest();
    public static final BlockCC slotCrafter = new SlotCrafter();


    public static void init(){
        slotChest.registerBlock();
        slotCrafter.registerBlock();
    }

}
