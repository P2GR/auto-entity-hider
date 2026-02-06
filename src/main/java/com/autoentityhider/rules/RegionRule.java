package com.autoentityhider.rules;

import net.runelite.client.plugins.autoentityhider.AutoEntityHiderConfig;
import net.runelite.client.plugins.autoentityhider.regions.RegionId;

import java.util.function.Predicate;

/**
 * Represents a rule that maps a region to a config toggle.
 */
public final class RegionRule
{
	private final String name;
	private final RegionId region;
	private final Predicate<AutoEntityHiderConfig> configToggle;

	public RegionRule(String name, RegionId region, Predicate<AutoEntityHiderConfig> configToggle)
	{
		this.name = name;
		this.region = region;
		this.configToggle = configToggle;
	}

	public String getName()
	{
		return name;
	}

	public int getRegionId()
	{
		return region.getId();
	}

	public boolean isEnabled(AutoEntityHiderConfig config)
	{
		return configToggle.test(config);
	}
}
