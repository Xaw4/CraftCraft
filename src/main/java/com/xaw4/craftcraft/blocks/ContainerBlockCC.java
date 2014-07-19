package com.xaw4.craftcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class ContainerBlockCC extends BlockCC implements ITileEntityProvider{

	protected ContainerBlockCC(String name) {
		super(name);
		this.isBlockContainer = true;
	}
	
	@Override
	public void onBlockAdded(World p_149726_1_, int p_149726_2_,
			int p_149726_3_, int p_149726_4_) {
		super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
	}
	
	@Override
	public void breakBlock(World world, int p_149749_2_, int p_149749_3_,
			int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
		super.breakBlock(world, p_149749_2_, p_149749_3_, p_149749_4_,
				p_149749_5_, p_149749_6_);
		world.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
	}
	
	@Override
	public boolean onBlockEventReceived(World world, int p_149696_2_,
			int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_) {
		super.onBlockEventReceived(world, p_149696_2_, p_149696_3_,
				p_149696_4_, p_149696_5_, p_149696_6_);
        TileEntity tileentity = world.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
        return tileentity != null ? tileentity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
	}

}
