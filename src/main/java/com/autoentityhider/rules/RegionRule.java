package com.autoentityhider.rules;

import com.autoentityhider.AutoEntityHiderConfig;
import com.autoentityhider.regions.RegionId;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Represents a rule that maps a region to a config toggle.
 */
public final class RegionRule
{
	private final String name;
	private final int[] regionIds;
	private final Predicate<AutoEntityHiderConfig> configToggle;

	public RegionRule(String name, RegionId region, Predicate<AutoEntityHiderConfig> configToggle)
	{
		this(name, new RegionId[]{region}, configToggle);
	}

	public RegionRule(String name, RegionId[] regions, Predicate<AutoEntityHiderConfig> configToggle)
	{
		this.name = name;
		this.regionIds = Arrays.stream(regions)
				.mapToInt(RegionId::getId)
				.toArray();
		this.configToggle = configToggle;
	}

	public String getName()
	{
		return name;
	}

	public boolean matchesRegion(int regionId)
	{
		return Arrays.stream(regionIds).anyMatch(id -> id == regionId);
	}

	public boolean isEnabled(AutoEntityHiderConfig config)
	{
		return configToggle.test(config);
	}
}
