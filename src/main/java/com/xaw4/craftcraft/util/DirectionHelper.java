package com.xaw4.craftcraft.util;

import net.minecraftforge.common.util.ForgeDirection;

public class DirectionHelper
{

	public static ForgeDirection getNext(ForgeDirection direction)
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
	
	public static int getNext(int direction){
		return getNext(ForgeDirection.getOrientation(direction)).ordinal();
	}

}
