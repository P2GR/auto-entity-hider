package com.autoentityhider.regions;

public enum GrandExchangeRegions implements RegionId
{
	GRAND_EXCHANGE(12598);

	private final int id;

	GrandExchangeRegions(int id)
	{
		this.id = id;
	}

	@Override
	public int getId()
	{
		return id;
	}
}
