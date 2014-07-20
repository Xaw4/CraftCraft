package com.xaw4.craftcraft.slotchest;

import com.xaw4.craftcraft.CraftCraft;
import com.xaw4.craftcraft.constants.TileEntityIds;
import com.xaw4.craftcraft.parents.AbstractSlotBlock;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Xaw4 on 14.07.2014.
 */
public class SlotChest extends AbstractSlotBlock {
	public static final String NAME = "slotChest";
	
    public SlotChest(){
        super(NAME, 3);
    }

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntitySlotChest();
	}
    
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
           FMLNetworkHandler.openGui(player, CraftCraft.instance, TileEntityIds.slotChest.ordinal(), world, x,y,z);
           FMLLog.fine("World Is Not Remote %s", world.getTileEntity(x,y,z).toString());
        }
        else
        {
            FMLLog.fine("World Is Remote %s", world.getTileEntity(x,y,z).toString());
        }
        return true;
    }
}
