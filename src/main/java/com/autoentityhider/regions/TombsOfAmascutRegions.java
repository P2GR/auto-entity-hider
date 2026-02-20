package com.autoentityhider.regions;

public enum TombsOfAmascutRegions implements RegionId
{
	LOBBY(14160),
	AKKHA_PUZZLE(14674),
	AKKHA_BOSS(14676),
	BABA_PUZZLE(15186),
	BABA_BOSS(15188),
	KEHPHRI_PUZZLE(14162),
	KEHPHRI_BOSS(14164),
	ZEBAK_PUZZLE(15698),
	ZEBAK_BOSS(15700),
	WARDEN_P1_P2(15184),
	WARDEN_P3_P4(15696),
	REWARD(14672);

	private final int id;

	TombsOfAmascutRegions(int id)
	{
		this.id = id;
	}

	@Override
	public int getId()
	{
		return id;
	}
}
