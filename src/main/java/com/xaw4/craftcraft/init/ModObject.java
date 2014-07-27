package com.xaw4.craftcraft.init;

public enum ModObject
{
	slotChest,
	slotCrafter;
	
	public final String unlocalizedName;
	public final int id;
	
	private ModObject(){
		this.unlocalizedName = name();
		this.id = ordinal();
	}
	
}
