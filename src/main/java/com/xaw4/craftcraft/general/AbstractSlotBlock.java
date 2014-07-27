package com.xaw4.craftcraft.general;

import java.util.Random;

import com.xaw4.craftcraft.CraftCraft;
import com.xaw4.craftcraft.constants.ModProperties;
import com.xaw4.craftcraft.init.ModObject;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class AbstractSlotBlock extends BlockCC implements
		ITileEntityProvider
{
	private final ModObject modObject;
	
	private Random random;
	
	@SideOnly(Side.CLIENT)
	protected IIcon frontIcon;

	// top = bot
	@SideOnly(Side.CLIENT)
	protected IIcon topIcon;

	protected AbstractSlotBlock(ModObject modobject)
	{
		super(modobject.unlocalizedName);
		this.isBlockContainer = true;
		this.random = new Random();
		this.modObject = modobject;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);

		this.frontIcon = iconRegister.registerIcon(this.getQualifiedName()
				+ ModProperties.POSTFIX_FRONT);
		FMLLog.info("register front icon %s", this.frontIcon.toString());
		this.topIcon = iconRegister.registerIcon(this.getQualifiedName()
				+ ModProperties.POSTFIX_TOP);
		FMLLog.info("register top icon %s", this.frontIcon.toString());

	}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		// used to render the Block as item
		if (side == ForgeDirection.NORTH.ordinal())
		{
			return frontIcon;
		}
		else if (side == 0 || side == 1)
		{
			return topIcon;
		}

		return super.getIcon(side, metadata);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#getIcon(net.minecraft.world.IBlockAccess, int, int, int, int)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide)
	{

		// used to render the block in the world
		TileEntity te = world.getTileEntity(x, y, z);
		int facing = 0;
		if (te instanceof AbstractSlotTileEntity)
		{
			AbstractSlotTileEntity slotEntity = (AbstractSlotTileEntity) te;
			facing = slotEntity.getFacing();
		}
		return blockSide == facing ? this.frontIcon : (blockSide == 0
				|| blockSide == 1 ? this.topIcon : this.blockIcon);

	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			FMLNetworkHandler.openGui(player, CraftCraft.instance,
					this.modObject.id, world, x, y, z);
			FMLLog.fine("World Is Not Remote %s", 
					world.getTileEntity(x, y, z).toString());
		}
		else
		{
			FMLLog.fine("World Is Remote %s", 
					world.getTileEntity(x, y, z).toString());
		}
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase player, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, player, stack);
		int heading = MathHelper
				.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		AbstractSlotTileEntity te = (AbstractSlotTileEntity) world
				.getTileEntity(x, y, z);
		switch (heading)
		{
			case 0:
				te.setFacing(ForgeDirection.NORTH);// (short) 2 );
				break;
			case 1:
				te.setFacing(ForgeDirection.EAST);//(short) 5);
				break;
			case 2:
				te.setFacing(ForgeDirection.SOUTH);//(short) 3);
				break;
			case 3:
				te.setFacing(ForgeDirection.WEST);//(short) 4);
				break;
			default:
				break;
		}
	}

	@Override
	public void onBlockAdded(World p_149726_1_, int p_149726_2_,
			int p_149726_3_, int p_149726_4_)
	{
		super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
	}


	@Override
	public boolean onBlockEventReceived(World world, int p_149696_2_,
			int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
	{
		super.onBlockEventReceived(world, p_149696_2_, p_149696_3_,
				p_149696_4_, p_149696_5_, p_149696_6_);
		TileEntity tileentity = world.getTileEntity(p_149696_2_, p_149696_3_,
				p_149696_4_);
		return tileentity != null ? tileentity.receiveClientEvent(p_149696_5_,
				p_149696_6_) : false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_)
    {
        AbstractSlotTileEntity tileentityslotchest = (AbstractSlotTileEntity)world.getTileEntity(x, y, z);

        if (tileentityslotchest != null)
        {
            for (int slotnum = 0; slotnum < tileentityslotchest.getSizeInventory(); ++slotnum)
            {
                ItemStack itemstack = tileentityslotchest.getStackInSlot(slotnum);

                if (itemstack != null)
                {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
                    {
                        int j1 = this.random.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize)
                        {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.random.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                    }
                }
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, p_149749_6_);
		world.removeTileEntity(x, y, z);
    }

}
