package com.xaw4.craftcraft.handler;

import com.xaw4.craftcraft.constants.ModProperties;
import com.xaw4.craftcraft.messages.MessageSlotAssignment;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler
{
	private static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE
			.newSimpleChannel(ModProperties.MOD_ID);

	public static void init()
	{
		int cnt = 0;
		INSTANCE.registerMessage(MessageSlotAssignment.class,
				MessageSlotAssignment.class, cnt++, Side.SERVER);
	}

	public static SimpleNetworkWrapper getInstance()
	{
		return INSTANCE;
	}
}
