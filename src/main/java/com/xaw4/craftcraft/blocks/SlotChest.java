package com.xaw4.craftcraft.blocks;

import com.xaw4.craftcraft.CraftCraft;
import com.xaw4.craftcraft.tileentity.TileEntitySlotChest;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Xaw4 on 14.07.2014.
 */
public class SlotChest extends ContainerBlockCC {
	public static final String NAME = "slotChest";
	
    public SlotChest(){
        super(NAME);
    }

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new TileEntitySlotChest();
	}
    
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
           FMLNetworkHandler.openGui(player, CraftCraft.instance, TileEntitySlotChest.GUI_ID, world, x,y,z);
           FMLLog.info("World Is Not Remote %s", world.getTileEntity(x,y,z).toString());
        }
        else
        {
            FMLLog.info("World Is Remote %s", world.getTileEntity(x,y,z).toString());
        }
        return true;
    }
}
