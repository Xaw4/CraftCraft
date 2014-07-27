package com.xaw4.craftcraft.slotchest;

import com.xaw4.craftcraft.CraftCraft;
import com.xaw4.craftcraft.general.AbstractSlotBlock;
import com.xaw4.craftcraft.init.ModObject;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Xaw4 on 14.07.2014.
 */
public class BlockSlotChest extends AbstractSlotBlock
{

	public static final String NAME = ModObject.slotChest.unlocalizedName;

	public BlockSlotChest()
	{
		super(ModObject.slotChest);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntitySlotChest();
	}
}
