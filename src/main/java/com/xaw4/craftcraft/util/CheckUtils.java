package com.xaw4.craftcraft.util;

public class CheckUtils
{
	public static boolean isInRange(int toTest, int lowerBoundIncluded, int upperBoundExlusive)
	{
		return lowerBoundIncluded <= toTest && toTest < upperBoundExlusive;
	}
}
