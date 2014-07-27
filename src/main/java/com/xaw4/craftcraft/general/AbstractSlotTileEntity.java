package com.xaw4.craftcraft.general;

import com.xaw4.craftcraft.slotchest.ContainerSlotChest;
import com.xaw4.craftcraft.util.RelativeFace;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class AbstractSlotTileEntity extends TileEntity implements
		ISidedInventory
{

	protected static final int STACK_COUNT = 6;

	/** the key used in the NBT for the slot number of a single slot */
	protected static final String SLOT_KEY_NBT = "SlotNum";
	/** the key used in the NBT for all slots (the root TagList) */
	protected static final String SLOTS_KEY_NBT = "Slots";
	/** the key used in the NBT for all slots (the root TagList) */
	protected static final String FACING_KEY_NBT = "Facing";

	private FaceData faceData;

	private FaceConfiguration faceConfiguration;

	private ItemStack[] slots;
	

//	private static final Integer[] dummyconfig = { 0, 2, 2, 2, null, null };

	private static final Integer[] nullconfig = new Integer[6];

	public AbstractSlotTileEntity()
	{
		faceData = new FaceData();
		faceConfiguration = new FaceConfiguration();
		slots = new ItemStack[STACK_COUNT];
	}

	public void assignSlot(RelativeFace face, int slot)
	{
		faceConfiguration.setAssignedSlot(face, slot);
	}
	
	
	/**
	 * inventory used for the GUIHandler
	 * @param inventory
	 * @param slotTileEntity
	 * @return
	 */
	public abstract AbstractSlotContainer getServerContainer(int guiId, InventoryPlayer inventory, AbstractSlotTileEntity slotTileEntity);
	
	
	/**
	 * Client gui for GUIHandler
	 * @param inventory
	 * @param slotTileEntity
	 * @return
	 */
	public abstract AbstractSlotGui getClientGui(int guiId, InventoryPlayer inventory, AbstractSlotTileEntity slotTileEntity);
	
	
	/**
	 * selects the next assigned slot for the face 
	 * @param face the face to be changed
	 * @return number of the selected slot (or FaceConfiguration.UNASSIGNED)
	 */
	public int incrementSlotAssignment(RelativeFace face)
	{
		return faceConfiguration.incrementSlotAssignment(face);
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer)
	{
		return entityPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
				zCoord + 0.5) <= 64;
	}

	
	
	/* **************
	 * IInventory
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
		if (0 <= slotNum && slotNum < STACK_COUNT)
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
	 * ISidedInventory
	 */
	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		ForgeDirection direction = ForgeDirection.getOrientation(side);
		int slot = faceConfiguration.getAssignedSlot(faceData
				.getRelativeFaceForDirection(direction));

		FMLLog.finer("acessible slot for: %d = %s ", side, slot);

		if (slot == FaceConfiguration.UNASSIGNED)
		{
			return new int[0];
		}
		int[] faces = { slot };
		return faces;
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
			if (slot != null)
			{
				NBTTagCompound itemCompound = new NBTTagCompound();
				itemCompound.setByte(SLOT_KEY_NBT, i);
				slot.writeToNBT(itemCompound);
				slotTagList.appendTag(itemCompound);
			}
		}
		compound.setTag(SLOTS_KEY_NBT, slotTagList);
		compound.setByte(FACING_KEY_NBT, (byte) this.getFacing());
		faceConfiguration.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		NBTTagList slotTagList = compound.getTagList(SLOTS_KEY_NBT,
				Constants.NBT.TAG_COMPOUND);
		for (byte i = 0; i < slotTagList.tagCount(); i++)
		{
			NBTTagCompound slotCompound = slotTagList.getCompoundTagAt(i);
			int slotNum = slotCompound.getByte(SLOT_KEY_NBT);
			if (0 <= slotNum && slotNum <= this.getSizeInventory())
			{
				ItemStack slotContent = ItemStack
						.loadItemStackFromNBT(slotCompound);
				setInventorySlotContents(slotNum, slotContent);
			}
		}
		byte facingNbt = compound.getByte(FACING_KEY_NBT);
		if (facingNbt >= 2 && facingNbt < 6)
		{
			this.setFacing(ForgeDirection.getOrientation(facingNbt));
		}
		faceConfiguration.readFromNBT(compound);
	}
	
	/* ***********
	 * Networking
	 */
	
	@Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbtTag);
       
    }
   
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }

	/* *******************
	 * local getters and setters
	 */
	public int getFacing()
	{
		return faceData.getFront().ordinal();
	}

	public void setFacing(ForgeDirection facing)
	{
		this.faceData.setFront(facing);
	}

	public FaceData getFaceData()
	{
		return faceData;
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
	
	public int getAssignedSlot(RelativeFace face)
	{
		return faceConfiguration.getAssignedSlot(face);
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
	
	public void log()
	{
		faceConfiguration.log();
		faceData.log();
	}

}
