package com.xaw4.craftcraft.slotcrafter;

import com.xaw4.craftcraft.general.AbstractSlotBlock;
import com.xaw4.craftcraft.general.BlockCC;
import com.xaw4.craftcraft.init.ModObject;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Xaw4 on 25.06.2014.
 */
public class BlockSlotCrafter extends AbstractSlotBlock
{
	public static String NAME = ModObject.slotCrafter.unlocalizedName;

	public BlockSlotCrafter()
	{
		super(ModObject.slotCrafter);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntitySlotCrafter();
	}
}
