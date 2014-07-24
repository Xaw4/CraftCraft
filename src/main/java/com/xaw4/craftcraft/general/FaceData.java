package com.xaw4.craftcraft.general;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.Lists;
import com.xaw4.craftcraft.util.RelativeFace;

import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * that class translates relative facing (front, left..) to 
 * absolute facing (NORTH, SOUTH) back and forth;
 * @author Xaw4
 *
 */
public class FaceData
{
	/** the index are the relative faces, values are the directions */
	ForgeDirection[] faces;
	
	public FaceData(int front)
	{
		faces = new ForgeDirection[6];
		this.setFront(ForgeDirection.getOrientation(front));
	}
	
	public FaceData()
	{
		faces = new ForgeDirection[6];
		this.setFront(ForgeDirection.NORTH);
	}
	
	public void setFront(ForgeDirection front)
	{
		ForgeDirection right = next(front);
		ForgeDirection back = next(right);
		ForgeDirection left = next(back);

		faces[RelativeFace.BOTTOM.ordinal()]= ForgeDirection.DOWN;
		faces[RelativeFace.TOP.ordinal()]= ForgeDirection.UP;
		faces[RelativeFace.FRONT.ordinal()]= front;
		faces[RelativeFace.RIGHT.ordinal()]= right;
		faces[RelativeFace.BACK.ordinal()]= back;
		faces[RelativeFace.LEFT.ordinal()]= left;
		this.log();
	}

//	public void setFront(int front){
//		this.setFront(ForgeDirection.getOrientation(front));
//	}
	
	/**
	 * gets the next direction ( N->W->S->E->N ) or ForgeDirection.UNKNOWN
	 */
	public static ForgeDirection next(ForgeDirection direction)
	{
		switch (direction)
		{
			case NORTH:
				return ForgeDirection.WEST;
			case WEST:
				return ForgeDirection.SOUTH;
			case SOUTH:
				return ForgeDirection.EAST;
			case EAST:
				return ForgeDirection.NORTH;

			default:
				return ForgeDirection.UNKNOWN;
		}
	}
	
	public static int next(int direction){
		return next(ForgeDirection.getOrientation(direction)).ordinal();
	}
	
		
	public ForgeDirection getDirectionForFace(RelativeFace face)
	{
		return faces[face.ordinal()];
	}
		
	public RelativeFace getRelativeFaceForDirection(ForgeDirection direction)
	{
		for(short i = 0; i< faces.length; ++i)
		{
			if(faces[i] == direction)
			{
				return RelativeFace.values()[i];
			}
		}
		return null;
	}
	
	/**
	 * @return the front
	 */
	public ForgeDirection getFront()
	{
		return getDirectionForFace(RelativeFace.FRONT);
	}

	public void log()
	{
		FMLLog.info("FaceData: %s", Lists.newArrayList(faces).toString());
	}

}
