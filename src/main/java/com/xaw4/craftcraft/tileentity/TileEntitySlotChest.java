package com.xaw4.craftcraft.tileentity;

import net.minecraft.item.ItemStack;

public class TileEntitySlotChest extends TileEntitySlotGeneric
{

	public static int GUI_ID = 1;

	private static final int STACK_COUNT = 6;

	private int stackLimit = 64;
	
	private ItemStack[] slots = new ItemStack[STACK_COUNT];

	@Override
	public void updateEntity()
	{
	}

	@Override
	public int getSizeInventory()
	{
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int slotNum)
	{
		if(0 <= slotNum && slotNum < STACK_COUNT)
		{
			return slots[slotNum];
		}
		return null;
	}

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
	public void setInventorySlotContents(int slotNum, ItemStack itemStack)
	{
		if(0 <= slotNum && slotNum < STACK_COUNT)
		{
			slots[slotNum] = itemStack;
		}
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
}
