package com.xaw4.craftcraft.general;

import org.lwjgl.util.Point;

import com.xaw4.craftcraft.general.AbstractSlotTileEntity;
import com.xaw4.craftcraft.util.RelativeFace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class AbstractSlotContainer<TE extends AbstractSlotTileEntity> extends Container
{
	private TE te;

	/** actual width + spacing */
	protected static final int SLOT_WIDTH = 18+4; 
	
	protected final Point firstSlot; 

	protected final Point playerInventory;
	
	private static final int SLOT_COUNT = 6;

	public AbstractSlotContainer(InventoryPlayer inventoryPlayer,
			TE te)
	{
		this.te = te;
		firstSlot = getFirstSlot();
		playerInventory = getPlayerInventory();
		addChestSlots();
		bindPlayerInventory(inventoryPlayer);
		addCustomSlots();
	}
	
	/**
	 * 
	 * @return the coordinates of the first (top, left) slot slots
	 */
	protected abstract Point getFirstSlot();
	
	/**
	 * 
	 * @return the coordinates of the player inventory (top, left pixel)
	 */
	protected abstract Point getPlayerInventory();
	
	public void assignSlot(RelativeFace face, int slot)
	{
		te.assignSlot(face, slot);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer)
	{
		return te.isUseableByPlayer(entityPlayer);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		int invx = playerInventory.getX();
		int invy = playerInventory.getY() - (4+3*18);
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
						invx + j * 18, invy + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 
					playerInventory.getX() + i * 18, playerInventory.getY()));
		}
	}

	protected void addChestSlots()
	{
		for (int slotNum = 0; slotNum < SLOT_COUNT; slotNum++)
		{
			addSlotToContainer(new Slot(te, slotNum, 
					firstSlot.getX() + slotNum * SLOT_WIDTH,
					firstSlot.getY()));
		}
	}
	
	/**
	 * used as a hook for inheriting classes (is called after bindUserInventory and addChestSlots())
	 */
	protected void addCustomSlots(){}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack())
		{
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			// merges the item into player inventory since its in the tileEntity
			if (slot < SLOT_COUNT)
			{
				if (!this.mergeItemStack(stackInSlot, 0, 35, true))
				{
					return null;
				}
			}
			// places it into the tileEntity is possible since its in the player
			// inventory
//			else if (!this.mergeItemStack(stackInSlot, 0, SLOT_COUNT, false))
//			{
//				return null;
//			}

			if (stackInSlot.stackSize == 0)
			{
				slotObject.putStack(null);
			}
			else
			{
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize)
			{
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}
	
	public TE getTileEntity(){
		return te;
	}
}
