package com.xaw4.craftcraft.handler;

import com.xaw4.craftcraft.CraftCraft;
import com.xaw4.craftcraft.general.AbstractSlotTileEntity;
import com.xaw4.craftcraft.init.ModObject;
import com.xaw4.craftcraft.slotchest.ContainerSlotChest;
import com.xaw4.craftcraft.slotchest.GuiSlotChest;
import com.xaw4.craftcraft.slotchest.TileEntitySlotChest;
import com.xaw4.craftcraft.slotcrafter.ContainerSlotCrafter;
import com.xaw4.craftcraft.slotcrafter.GuiSlotCrafter;
import com.xaw4.craftcraft.slotcrafter.TileEntitySlotCrafter;

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
		if(te != null && te instanceof AbstractSlotTileEntity)
		{
			AbstractSlotTileEntity slotTE = (AbstractSlotTileEntity) te;
			return slotTE.getServerContainer(id, player.inventory, slotTE);
//			if(id == ModObject.slotChest.id && te instanceof TileEntitySlotChest)
//			{
//				TileEntitySlotChest tesc = (TileEntitySlotChest) te;
//				tesc.log();
//				return new ContainerSlotChest(player.inventory, tesc);
//			}
//			else if(id == ModObject.slotCrafter.id && te instanceof TileEntitySlotChest)
//			{
//				TileEntitySlotCrafter tesc = (TileEntitySlotCrafter) te;
//				tesc.log();
//				return new ContainerSlotCrafter(player.inventory, tesc);
//			}
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null)
		{
			if (te instanceof AbstractSlotTileEntity)
			{
				AbstractSlotTileEntity slotTE = (AbstractSlotTileEntity) te;
				return slotTE.getClientGui(id, player.inventory, slotTE);
//				if (id == ModObject.slotChest.id
//						&& te instanceof TileEntitySlotChest)
//				{
//					TileEntitySlotChest tesc = (TileEntitySlotChest) te;
//					tesc.log();
//					return new GuiSlotChest(player.inventory, tesc);
//				}
//				else if(id == ModObject.slotCrafter.id
//						&& te instanceof TileEntitySlotCrafter)
//				{
//					TileEntitySlotCrafter tesc = (TileEntitySlotCrafter) te;
//					tesc.log();
//					return new GuiSlotCrafter(player.inventory, tesc);
//				}
			}
		}
		return null;
	}
	
}
