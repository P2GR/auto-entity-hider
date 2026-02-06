package com.autoentityhider.regions;

public enum MinigameRegions implements RegionId
{
	WINTERTODT_BOSS(6462),
	WINTERTODT_BANK(6461),
	TEMPOROSS_BOSS(12076),
	TEMPOROSS_LOBBY(12588),
	GUARDIANS_OF_THE_RIFT(14484);

	private final int id;

	MinigameRegions(int id)
	{
		this.id = id;
	}

	@Override
	public int getId()
	{
		return id;
	}
}
