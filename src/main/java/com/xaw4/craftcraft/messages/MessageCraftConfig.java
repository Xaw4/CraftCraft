package com.xaw4.craftcraft.messages;

import net.minecraft.inventory.Container;

import com.xaw4.craftcraft.general.AbstractSlotContainer;
import com.xaw4.craftcraft.general.FaceConfiguration;
import com.xaw4.craftcraft.slotchest.ContainerSlotChest;
import com.xaw4.craftcraft.slotchest.BlockSlotChest;
import com.xaw4.craftcraft.slotcrafter.ContainerSlotCrafter;
import com.xaw4.craftcraft.util.RelativeFace;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageCraftConfig implements IMessage,
		IMessageHandler<MessageCraftConfig, IMessage>
{
	public static final int INCREASE_MESSAGE = 1;
	public static final int DECREASE_MESSAGE = -1;
	
	private int craftingIdx;
	private int increaseSlot;

	public MessageCraftConfig(){}
	
	/**
	 * 
	 * @param craftingIdx
	 * @param increaseSlot should either be INCREASE_MESSAGE or DECREASE_MESSAGE
	 */
	public MessageCraftConfig(int craftingIdx, int increaseSlot)
	{
		this.craftingIdx = craftingIdx;
		this.increaseSlot = increaseSlot;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		if (buf.readableBytes() == 2)
		{
			this.craftingIdx = buf.getByte(0);
			this.increaseSlot = buf.getByte(1);
		}

	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		byte[] bytes = {(byte)craftingIdx, (byte)increaseSlot};
		buf.writeBytes(bytes);
	}

	@Override
	public IMessage onMessage(MessageCraftConfig message, MessageContext ctx)
	{
		Container container = ctx.getServerHandler().playerEntity.openContainer;
		
		if(container != null && container instanceof ContainerSlotCrafter)
		{
			ContainerSlotCrafter sc= (ContainerSlotCrafter) container;
			if(message.increaseSlot == INCREASE_MESSAGE){
				sc.getTileEntity().increaseCraftingConfig(message.craftingIdx);
			}
			else if (message.increaseSlot == DECREASE_MESSAGE)
			{
				sc.getTileEntity().decreaseCraftingConfig(message.craftingIdx);
			}
		}
		
		return null;
	}
}
