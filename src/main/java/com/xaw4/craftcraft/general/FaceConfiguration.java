package com.xaw4.craftcraft.general;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

import javax.swing.plaf.ListUI;

import net.minecraft.nbt.NBTTagCompound;

import com.google.common.collect.Lists;
import com.xaw4.craftcraft.util.RelativeFace;

import cpw.mods.fml.common.FMLLog;

/**
 * 
 * @author Xaw4
 * 
 */
public class FaceConfiguration
{
	/**
	 * the array values correspond to the sides BOT, TOP, Front, Right, Back,
	 * Left null => not set
	 */
	public int[] assignedSlots = new int[6];

	public static final int UNASSIGNED = Byte.MIN_VALUE;

	public static final String CONFIGURATION_KEY_NBT = "Configuration";

	public FaceConfiguration()
	{
	}

	public FaceConfiguration(final int[] assignedSlots)
	{
		if (assignedSlots.length == 6)
		{
			this.assignedSlots = assignedSlots;
		}
	}

	public void setAssignedSlot(RelativeFace face, final int slot)
	{
		assignedSlots[face.ordinal()] = 0 <= slot && slot < 6 ? slot : UNASSIGNED;
	}

	public Integer getAssignedSlot(RelativeFace face)
	{
		return assignedSlots[face.ordinal()];
	}

	public RelativeFace[] getFacesAssignedToSlot(int slot)
	{
		List<RelativeFace> faces = new ArrayList<RelativeFace>();
		for (Integer assignee : assignedSlots)
		{
			if (assignee != null && assignee.equals(slot))
			{
				faces.add(RelativeFace.values()[assignee]);
			}
		}

		return faces.toArray(new RelativeFace[faces.size()]);
	}

	/*
	 * nbt stuffs
	 */
	/**
	 * writes to the compound (using an intArray with key
	 * FaceConfiguration.CONFIGURATION_KEY_NBT)
	 * 
	 * @param compound
	 *            the compound to read to using key
	 *            FaceConfiguration.CONFIGURATION_KEY_NBT
	 */
	public void writeToNBT(NBTTagCompound compound)
	{
		int[] arrayForNBT = new int[assignedSlots.length];
		for (short i = 0; i < arrayForNBT.length; i++)
		{
			arrayForNBT[i] = assignedSlots[i];
		}
		compound.setIntArray(CONFIGURATION_KEY_NBT, arrayForNBT);
	}

	/**
	 * reads from the given nbt an builts a config
	 * 
	 * returns false if not successful (no nbt data found or stored array is not
	 * exactly 6 long)
	 * 
	 * @param compound
	 *            the compound to read to using key
	 *            FaceConfiguration.CONFIGURATION_KEY_NBT
	 * @return true if successfull, false otherwise
	 */
	public boolean readFromNBT(NBTTagCompound compound)
	{
		int[] nbtArray = compound.getIntArray(CONFIGURATION_KEY_NBT);
		if (nbtArray.length != assignedSlots.length)
		{
			FMLLog.info("NBT data could not be read (%s = %s)",
					CONFIGURATION_KEY_NBT, nbtArray);
			return false;
		}

		// map the actual data
		for (int i = 0; i < nbtArray.length; i++)
		{
			if (0 <= nbtArray[i] && nbtArray[i] < 6)
			{
				assignedSlots[i] = nbtArray[i];
			}
			else
			{
				assignedSlots[i] = UNASSIGNED;
			}
		}
		return true;
	}

	/**
	 * @return the assignedSlots
	 */
	public int[] getAssignedSlots()
	{
		return assignedSlots;
	}

	/**
	 * @param assignedSlots
	 *            the assignedSlots to set
	 */
	public void setAssignedSlots(int[] assignedSlots)
	{
		this.assignedSlots = assignedSlots;
	}
	
	/**
	 * selects the next assigned slot for the face 
	 * @param face the face to be changed
	 * @return number of the selected slot (or FaceConfiguration.UNASSIGNED)
	 */
	public int incrementSlotAssignment(RelativeFace face){
		int faceIdx= face.ordinal();
		if(assignedSlots[faceIdx] == UNASSIGNED)
		{
				assignedSlots[faceIdx] = 0;
		}
		else if(assignedSlots[faceIdx] >=5)
		{
				assignedSlots[faceIdx] = UNASSIGNED;
		}		
		else
		{
				assignedSlots[faceIdx]++;
		}
		return assignedSlots[faceIdx];
	}

	public void log()
	{
		StringBuffer sb = new StringBuffer("[");
		for (int slot : assignedSlots)
		{
			sb.append(slot);
		}
		sb.append(" ]");
		FMLLog.info("FaceConfiguration: %s", sb);
	}
}
