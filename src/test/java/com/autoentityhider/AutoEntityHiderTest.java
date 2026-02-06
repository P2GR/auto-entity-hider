package com.autoentityhider;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class AutoEntityHiderTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(AutoEntityHiderPlugin.class);
		RuneLite.main(args);
	}
}