package com.xaw4.craftcraft.gui;

import com.xaw4.craftcraft.CraftCraft;
import com.xaw4.craftcraft.container.ContainerSlotChest;
import com.xaw4.craftcraft.tileentity.TileEntitySlotChest;
import com.xaw4.craftcraft.tileentity.TileEntitySlotGeneric;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

/**
 * 
 * @author Xaw4
 *
 */

public class GuiHandler implements IGuiHandler {

	
	private static GuiHandler instance;
	
	public static GuiHandler getInstance(){
		if(instance == null){
			instance = new GuiHandler();
		}
		return instance;
	}
	
	public static void init()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(CraftCraft.instance, getInstance());
		FMLLog.info("CraftCraft: GUI Handler Initialized");
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TileEntitySlotGeneric)
		{
			if(id == TileEntitySlotChest.GUI_ID && te instanceof TileEntitySlotChest)
			{
				return new ContainerSlotChest(player.inventory, (TileEntitySlotChest) te);
			}
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TileEntitySlotGeneric)
		{
			if(id == TileEntitySlotChest.GUI_ID && te instanceof TileEntitySlotChest)
			{
				return new GuiSlotChest(player.inventory, (TileEntitySlotChest) te);
			}
		}
		return null;
	}
	
	
	
}
