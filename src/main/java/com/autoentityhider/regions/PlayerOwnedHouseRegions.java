package com.autoentityhider.regions;

public enum PlayerOwnedHouseRegions implements RegionId
{
	PLAYER_OWNED_HOUSE_1(7514),
	PLAYER_OWNED_HOUSE_2(7770),
	PLAYER_OWNED_HOUSE_3(8026),
	PLAYER_OWNED_HOUSE_4(7257),
	PLAYER_OWNED_HOUSE_5(7513),
	PLAYER_OWNED_HOUSE_6(7769),
	PLAYER_OWNED_HOUSE_7(8025);

	private final int id;

	PlayerOwnedHouseRegions(int id)
	{
		this.id = id;
	}

	@Override
	public int getId()
	{
		return id;
	}
}