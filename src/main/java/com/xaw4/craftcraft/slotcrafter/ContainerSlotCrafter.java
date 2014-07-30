package com.xaw4.craftcraft.slotcrafter;

import org.lwjgl.util.Point;

import com.xaw4.craftcraft.general.AbstractSlotContainer;
import com.xaw4.craftcraft.general.AbstractSlotTileEntity;
import com.xaw4.craftcraft.slotcrafter.inventory.CraftingSlot;
import com.xaw4.craftcraft.slotcrafter.inventory.InventoryCraftingGrid;
import com.xaw4.craftcraft.slotcrafter.inventory.OutputSlot;
import com.xaw4.craftcraft.util.RelativeFace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSlotCrafter extends AbstractSlotContainer
{
	protected final Point craftingGrid;

	public ContainerSlotCrafter(InventoryPlayer inventoryPlayer,
			TileEntitySlotCrafter te)
	{
		super(inventoryPlayer, te);
		craftingGrid = getCraftingGridPosition();
	}

	@Override
	protected Point getFirstSlot()
	{
		return new Point(25, 62 + 24);
	}

	@Override
	protected Point getPlayerInventory()
	{
		return new Point(8, 142 + 24);
	}

	protected Point getCraftingGridPosition()
	{
		if (craftingGrid == null)
		{
			return new Point(117, 8);
		}
		else
		{
			return craftingGrid;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xaw4.craftcraft.general.AbstractSlotContainer#addCustomSlots()
	 */
	@Override
	protected void addCustomSlots()
	{
		TileEntitySlotCrafter te = (TileEntitySlotCrafter) getTileEntity();
		InventoryCraftingGrid grid = te.getCraftingGrid();
		for (int ix = 0; ix < 3; ++ix)
		{
			for (int iy = 0; iy < 3; ++iy)
			{
				addSlotToContainer(new CraftingSlot(grid, ix + iy * 3,
						getCraftingGridPosition().getX() + ix * 18,
						getCraftingGridPosition().getY() + iy * 18));
				
			}
		}
	}

	protected void addChestSlots()
	{
		for (int slotNum = 0; slotNum < 5; slotNum++)
		{
			addSlotToContainer(new Slot(getTileEntity(), slotNum,
					firstSlot.getX() + slotNum * SLOT_WIDTH,
					firstSlot.getY()));
		}
		addSlotToContainer(new OutputSlot(getTileEntity(), 5,
				firstSlot.getX() + 5 * SLOT_WIDTH,
				firstSlot.getY()));
	}

}
