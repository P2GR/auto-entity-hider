package com.autoentityhider.regions;

public enum TheatreOfBloodRegions implements RegionId
{
	MAIDEN(12613),
	BLOAT(13125),
	NYLOS(13122),
	SOTETSEG(13123),
	XARPUS(12612),
	VERZIK(12611),
	REWARD(12867);

	private final int id;

	TheatreOfBloodRegions(int id)
	{
		this.id = id;
	}

	@Override
	public int getId()
	{
		return id;
	}
}
