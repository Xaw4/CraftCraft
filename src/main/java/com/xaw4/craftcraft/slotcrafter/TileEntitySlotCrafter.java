package com.xaw4.craftcraft.slotcrafter;

import com.xaw4.craftcraft.general.AbstractSlotContainer;
import com.xaw4.craftcraft.general.AbstractSlotGui;
import com.xaw4.craftcraft.general.AbstractSlotTileEntity;
import com.xaw4.craftcraft.init.ModObject;
import com.xaw4.craftcraft.slotchest.ContainerSlotChest;
import com.xaw4.craftcraft.slotchest.GuiSlotChest;
import com.xaw4.craftcraft.slotchest.TileEntitySlotChest;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

/**
 * @author Xaw4
 * 
 */
public class TileEntitySlotCrafter extends AbstractSlotTileEntity
{
	private int stackLimit = 64;

	// IInventory

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.IInventory#decrStackSize(int, int)
	 */
	@Override
	public ItemStack decrStackSize(int slotNum, int count)
	{
		ItemStack stack = getStackInSlot(slotNum);
		if (stack != null)
		{
			if (stack.stackSize <= count)
			{
				setInventorySlotContents(slotNum, null);
			}
			else
			{
				stack = stack.splitStack(count);
			}
			this.markDirty();
		}
		return stack;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.IInventory#getStackInSlotOnClosing(int)
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int slotNum)
	{
		ItemStack stack = getStackInSlot(slotNum);
		setInventorySlotContents(slotNum, null);
		return stack;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.IInventory#getInventoryName()
	 */
	@Override
	public String getInventoryName()
	{
		return "SlotCrafer_Inv";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.IInventory#hasCustomInventoryName()
	 */
	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.IInventory#getInventoryStackLimit()
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return stackLimit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.IInventory#isItemValidForSlot(int,
	 * net.minecraft.item.ItemStack)
	 */
	@Override
	public boolean isItemValidForSlot(int slotNum, ItemStack itemStack)
	{
		return 0 <= slotNum && slotNum < 5;
	}

	// ISidedInventory
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.ISidedInventory#canInsertItem(int,
	 * net.minecraft.item.ItemStack, int)
	 */
	@Override
	public boolean canInsertItem(int slotNum, ItemStack item,
			int side)
	{
		return 0 <= slotNum && slotNum < 5;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.inventory.ISidedInventory#canExtractItem(int,
	 * net.minecraft.item.ItemStack, int)
	 */
	@Override
	public boolean canExtractItem(int slotNum, ItemStack item,
			int side)
	{
		return 0 <= slotNum && slotNum < 6;
	}
	
	@Override
	public AbstractSlotContainer getServerContainer(int guiId,
			InventoryPlayer inventory, AbstractSlotTileEntity slotTileEntity)
	{
		if(guiId == ModObject.slotCrafter.id && slotTileEntity instanceof TileEntitySlotCrafter)
		{
			TileEntitySlotCrafter te = (TileEntitySlotCrafter) slotTileEntity;
			te.log();
			return new ContainerSlotCrafter(inventory, te);
		}
		return null;
	}

	@Override
	public AbstractSlotGui getClientGui(int guiId, InventoryPlayer inventory,
			AbstractSlotTileEntity slotTileEntity)
	{
		if (guiId == ModObject.slotCrafter.id && slotTileEntity instanceof TileEntitySlotCrafter)
		{
			TileEntitySlotCrafter tesc = (TileEntitySlotCrafter) slotTileEntity;
			tesc.log();
			return new GuiSlotCrafter(inventory, tesc);
		}
		return null;
	}
}
