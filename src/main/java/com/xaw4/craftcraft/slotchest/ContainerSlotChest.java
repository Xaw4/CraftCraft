package com.xaw4.craftcraft.slotchest;

import org.lwjgl.util.Point;

import com.xaw4.craftcraft.general.AbstractSlotContainer;
import com.xaw4.craftcraft.general.AbstractSlotTileEntity;
import com.xaw4.craftcraft.util.RelativeFace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSlotChest extends AbstractSlotContainer
{
	public ContainerSlotChest(InventoryPlayer inventoryPlayer,
			TileEntitySlotChest te)
	{
		super(inventoryPlayer, te);
	}

	@Override
	protected Point getFirstSlot()
	{
		return new Point(25, 62);
	}

	@Override
	protected Point getPlayerInventory()
	{
		return new Point(8, 142);
	}
	
}
