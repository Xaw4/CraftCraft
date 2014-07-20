package com.xaw4.craftcraft.general;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntitySlotGeneric extends TileEntity implements
		ISidedInventory
{

	protected static final int STACK_COUNT = 6;
	/** the key used in the NBT for the slot number of a single slot */
	protected static final String SLOT_KEY_NBT = "SlotNum";
	/** the key used in the NBT for all slots (the root TagList) */
	protected static final String SLOTS_KEY_NBT = "Slots";
	
	private int facing = 2;


	private ItemStack[] slots = new ItemStack[STACK_COUNT];

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer)
	{
		return entityPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
				zCoord + 0.5) <= 64;
	}

	/* **************
	 *  IInventory
	 */
	@Override
	public int getSizeInventory()
	{
		return STACK_COUNT;
	}

	@Override
	public ItemStack getStackInSlot(int slotNum)
	{
		if (0 <= slotNum && slotNum < STACK_COUNT)
		{
			return slots[slotNum];
		}
		return null;
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
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
	}

	/* ****************
	 *  ISidedInventory
	 */
	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		int[] sides = { side == 0 || side == 1 ? side
				: (side - 2 + facing) % 4 + 2 };
		return sides;
	}

	/*
	 * NBT Stuff
	 */
	
	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		NBTTagList slotTagList = new NBTTagList();
		for (byte i = 0; i < slots.length; i++)
		{
			ItemStack slot = slots[i];
			if(slot != null){
				NBTTagCompound itemCompound = new NBTTagCompound();
				itemCompound.setByte(SLOT_KEY_NBT, i);
				slot.writeToNBT(itemCompound);
				slotTagList.appendTag(itemCompound);
			}
		}
		compound.setTag(SLOTS_KEY_NBT, slotTagList);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		NBTTagList slotTagList = compound.getTagList(SLOTS_KEY_NBT, Constants.NBT.TAG_COMPOUND);
		for(byte i = 0; i< slotTagList.tagCount(); i++)
		{
			NBTTagCompound slotCompound = slotTagList.getCompoundTagAt(i);
			int slotNum = slotCompound.getByte(SLOT_KEY_NBT);
			if(0 <= slotNum && slotNum <= this.getSizeInventory())
			{
				ItemStack slotContent = ItemStack.loadItemStackFromNBT(slotCompound);
				setInventorySlotContents(slotNum, slotContent);
			}
		}
	}

	

	
	/* *******************
	 * local getters and setters
	 */
	public int getFacing()
	{
		return facing;
	}

	public void setFacing(int facing)
	{
		this.facing = facing;
	}

	
	protected ItemStack getSlot(int index)
	{
		try
		{
			return slots[index];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			FMLLog.severe("can not get slot %d, (%s)", index, e.getMessage());
			return null;
		}
	}

	protected void setSlot(int index, ItemStack itemStack)
	{
		try
		{
			slots[index] = itemStack;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			FMLLog.severe("can not set slot %d to %s, (%s)", index,
					itemStack.toString(), e.getMessage());
		}
	}

}
