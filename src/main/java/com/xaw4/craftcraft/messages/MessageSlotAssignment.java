package com.xaw4.craftcraft.messages;

import net.minecraft.inventory.Container;

import com.xaw4.craftcraft.general.AbstractSlotContainer;
import com.xaw4.craftcraft.general.FaceConfiguration;
import com.xaw4.craftcraft.slotchest.ContainerSlotChest;
import com.xaw4.craftcraft.slotchest.BlockSlotChest;
import com.xaw4.craftcraft.util.RelativeFace;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSlotAssignment implements IMessage,
		IMessageHandler<MessageSlotAssignment, IMessage>
{

	private RelativeFace face;
	private int assignedSlot;

	public MessageSlotAssignment()
	{
	}

	public MessageSlotAssignment(RelativeFace face, int assignedSlot)
	{
		this.face = face;
		this.assignedSlot = assignedSlot;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		if (buf.readableBytes() == 2)
		{
			byte faceIdx = buf.getByte(0);
			this.face = RelativeFace.values()[faceIdx];
			this.assignedSlot = buf.getByte(1);
		}

	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		byte[] bytes = {(byte)face.ordinal(), (byte)assignedSlot};
		buf.writeBytes(bytes);
	}

	@Override
	public IMessage onMessage(MessageSlotAssignment message, MessageContext ctx)
	{
		Container container = ctx.getServerHandler().playerEntity.openContainer;
		
		if(container != null && container instanceof AbstractSlotContainer)
		{
			AbstractSlotContainer sc= (AbstractSlotContainer) container;
			sc.assignSlot(message.face, message.assignedSlot);
		}
		
		return null;
	}
}
