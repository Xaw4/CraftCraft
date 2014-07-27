package com.xaw4.craftcraft.init;

import com.xaw4.craftcraft.constants.ModProperties;
import com.xaw4.craftcraft.slotchest.BlockSlotChest;
import com.xaw4.craftcraft.slotchest.TileEntitySlotChest;
import com.xaw4.craftcraft.slotcrafter.BlockSlotCrafter;
import com.xaw4.craftcraft.slotcrafter.TileEntitySlotCrafter;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntitiesCC {

	
	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntitySlotChest.class,
				ModProperties.MOD_ID + ":" + BlockSlotChest.NAME);
		
		GameRegistry.registerTileEntity(TileEntitySlotCrafter.class,
				ModProperties.MOD_ID + ":" + BlockSlotCrafter.NAME);
	}
}
