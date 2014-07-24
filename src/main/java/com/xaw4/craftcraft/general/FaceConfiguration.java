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
	public Integer[] assignedSlots = new Integer[6];

	private static final int UNASSIGNED = -1;

	public static final String CONFIGURATION_KEY_NBT = "Configuration";

	public FaceConfiguration()
	{
	}

	public FaceConfiguration(final Integer[] assignedSlots)
	{
		if (assignedSlots.length == 6)
		{
			this.assignedSlots = assignedSlots;
		}
	}

	public void setAssignedSlot(RelativeFace face, Integer slot)
	{
		assignedSlots[face.ordinal()] = slot;
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
			arrayForNBT[i] = assignedSlots[i] == null ?
					UNASSIGNED : assignedSlots[i];
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
				assignedSlots[i] = null;
			}
		}
		return true;
	}

	/**
	 * @return the assignedSlots
	 */
	public Integer[] getAssignedSlots()
	{
		return assignedSlots;
	}

	/**
	 * @param assignedSlots
	 *            the assignedSlots to set
	 */
	public void setAssignedSlots(Integer[] assignedSlots)
	{
		this.assignedSlots = assignedSlots;
	}


	public void log()
	{
		FMLLog.info("FaceConfiguration: %s", Lists.newArrayList(assignedSlots).toString());
	}
	
}
