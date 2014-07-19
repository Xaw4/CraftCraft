package com.xaw4.craftcraft.tileentity;

import com.xaw4.craftcraft.blocks.SlotChest;
import com.xaw4.craftcraft.constants.ModProperties;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntitiesCC {

	
	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntitySlotChest.class,
				ModProperties.MOD_ID + ":"+SlotChest.NAME);
	}
}
