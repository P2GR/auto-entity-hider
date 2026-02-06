package com.autoentityhider.regions;

public enum ChambersOfXericRegions implements RegionId
{
	OLM(12889);


	private final int id;

	ChambersOfXericRegions(int id)
	{
		this.id = id;
	}

	@Override
	public int getId()
	{
		return id;
	}
}
