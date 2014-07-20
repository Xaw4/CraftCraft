package com.xaw4.craftcraft.slotchest;

import com.xaw4.craftcraft.general.TileEntitySlotGeneric;

import net.minecraft.item.ItemStack;

public class TileEntitySlotChest extends TileEntitySlotGeneric
{
	private int stackLimit = 64;

	
	
	@Override
	public void updateEntity()
	{
	}

	//IInventory

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

	@Override
	public ItemStack getStackInSlotOnClosing(int slotNum)
	{
		ItemStack stack = getStackInSlot(slotNum);
        setInventorySlotContents(slotNum, null);
        return stack;
	}

	@Override
	public String getInventoryName()
	{
		return "SlotChest_Inv";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return stackLimit;
	}

	@Override
	public boolean isItemValidForSlot(int slotNum, ItemStack itemStack)
	{
		return true;
	}

	
	//ISidedInventory
	@Override
	public boolean canInsertItem(int slotNum, ItemStack item,
			int side)
	{
		return true;
	}

	@Override
	public boolean canExtractItem(int slotNum, ItemStack item,
			int side)
	{
		return true;
	}
}
