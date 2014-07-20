package com.xaw4.craftcraft.init;

import com.xaw4.craftcraft.constants.ModProperties;
import com.xaw4.craftcraft.slotchest.SlotChest;
import com.xaw4.craftcraft.slotchest.TileEntitySlotChest;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntitiesCC {

	
	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntitySlotChest.class,
				ModProperties.MOD_ID + ":"+SlotChest.NAME);
	}
}
