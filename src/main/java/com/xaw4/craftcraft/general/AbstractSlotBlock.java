package com.xaw4.craftcraft.general;

import com.xaw4.craftcraft.constants.ModProperties;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class AbstractSlotBlock extends BlockCC implements ITileEntityProvider{

    @SideOnly(Side.CLIENT)
    protected IIcon frontIcon;
    
    //top = bot
    @SideOnly(Side.CLIENT)
    protected IIcon topIcon;
	
    
	protected AbstractSlotBlock(String name)
	{
		super(name);
		this.isBlockContainer = true;
	}


	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);
		
    	this.frontIcon = iconRegister.registerIcon(this.getFullName() + ModProperties.POSTFIX_FRONT);
    	FMLLog.info("register front icon %s", this.frontIcon.toString());
    	this.topIcon = iconRegister.registerIcon(this.getFullName() + ModProperties.POSTFIX_TOP);
    	FMLLog.info("register top icon %s", this.frontIcon.toString());
        
	}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		// used to render the Block as item
		if(side == ForgeDirection.NORTH.ordinal()){
			return frontIcon;
		}
		else if (side == 0 || side ==1) 
		{
			return topIcon;
		}
		
		return super.getIcon(side, metadata);
	}
	
	
	@Override
	  @SideOnly(Side.CLIENT)
	  public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide) {

	    // used to render the block in the world
	    TileEntity te = world.getTileEntity(x, y, z);
	    int facing = 0;
	    if(te instanceof TileEntitySlotGeneric) {
	    	TileEntitySlotGeneric slotEntity = (TileEntitySlotGeneric) te;
	      facing = slotEntity.getFacing();
	    }
	    return blockSide == facing ? this.frontIcon : (
	    	blockSide == 0 || blockSide == 1 ? 
	    			this.topIcon : this.blockIcon);
	    
	  }
	@Override
	  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
	    super.onBlockPlacedBy(world, x, y, z, player, stack);
	    int heading = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
	    TileEntitySlotGeneric te = (TileEntitySlotGeneric) world.getTileEntity(x, y, z);
	    switch (heading) {
	    case 0:
	      te.setFacing((short) 2);
	      break;
	    case 1:
	      te.setFacing((short) 5);
	      break;
	    case 2:
	      te.setFacing((short) 3);
	      break;
	    case 3:
	      te.setFacing((short) 4);
	      break;
	    default:
	      break;
	    }
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
