package com.autoentityhider.rules;

import net.runelite.client.plugins.autoentityhider.AutoEntityHiderConfig;
import net.runelite.client.plugins.autoentityhider.regions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Central registry of all region rules for the Auto Entity Hider plugin.
 */
public final class RegionRules
{
	private RegionRules()
	{
		// Utility class.
	}

	private static final List<RegionRule> RULES = Arrays.asList(
		// Chambers of Xeric
		new RegionRule("COX: Olm", ChambersOfXericRegions.OLM, AutoEntityHiderConfig::coxOlm),

		// Tombs of Amascut
		new RegionRule("TOA: Lobby", TombsOfAmascutRegions.LOBBY, AutoEntityHiderConfig::toaLobby),
		new RegionRule("TOA: Akkha Puzzle", TombsOfAmascutRegions.AKKHA_PUZZLE, AutoEntityHiderConfig::toaAkkhaPuzzleRoom),
		new RegionRule("TOA: Akkha Boss", TombsOfAmascutRegions.AKKHA_BOSS, AutoEntityHiderConfig::toaAkkhaBossRoom),
		new RegionRule("TOA: Baba Puzzle", TombsOfAmascutRegions.BABA_PUZZLE, AutoEntityHiderConfig::toaBabaPuzzleRoom),
		new RegionRule("TOA: Baba Boss", TombsOfAmascutRegions.BABA_BOSS, AutoEntityHiderConfig::toaBabaBossRoom),
		new RegionRule("TOA: Kephri Puzzle", TombsOfAmascutRegions.KEHPHRI_PUZZLE, AutoEntityHiderConfig::toaKephriPuzzleRoom),
		new RegionRule("TOA: Kephri Boss", TombsOfAmascutRegions.KEHPHRI_BOSS, AutoEntityHiderConfig::toaKephriBossRoom),
		new RegionRule("TOA: Zebak Puzzle", TombsOfAmascutRegions.ZEBAK_PUZZLE, AutoEntityHiderConfig::toaZebakPuzzleRoom),
		new RegionRule("TOA: Zebak Boss", TombsOfAmascutRegions.ZEBAK_BOSS, AutoEntityHiderConfig::toaZebakBossRoom),
		new RegionRule("TOA: Warden", TombsOfAmascutRegions.WARDEN_OBELISK, AutoEntityHiderConfig::toaWardenObeliskRoom),
		new RegionRule("TOA: Reward", TombsOfAmascutRegions.REWARD, AutoEntityHiderConfig::toaRewardRoom),

		// Theatre of Blood
		new RegionRule("TOB: Maiden", TheatreOfBloodRegions.MAIDEN, AutoEntityHiderConfig::tobMaiden),
		new RegionRule("TOB: Bloat", TheatreOfBloodRegions.BLOAT, AutoEntityHiderConfig::tobBloat),
		new RegionRule("TOB: Nylos", TheatreOfBloodRegions.NYLOS, AutoEntityHiderConfig::tobNylos),
		new RegionRule("TOB: Sotetseg", TheatreOfBloodRegions.SOTETSEG, AutoEntityHiderConfig::tobSotetseg),
		new RegionRule("TOB: Xarpus", TheatreOfBloodRegions.XARPUS, AutoEntityHiderConfig::tobXarpus),
		new RegionRule("TOB: Verzik", TheatreOfBloodRegions.VERZIK, AutoEntityHiderConfig::tobVerzik),
		new RegionRule("TOB: Reward", TheatreOfBloodRegions.REWARD, AutoEntityHiderConfig::tobRewardRoom),

		// Locations
		new RegionRule("Grand Exchange", GrandExchangeRegions.GRAND_EXCHANGE, AutoEntityHiderConfig::grandExchange),

		// Minigames
		new RegionRule("Wintertodt: Boss", MinigameRegions.WINTERTODT_BOSS, AutoEntityHiderConfig::wintertodtBoss),
		new RegionRule("Wintertodt: Bank", MinigameRegions.WINTERTODT_BANK, AutoEntityHiderConfig::wintertodtBank),
		new RegionRule("Tempoross: Boss", MinigameRegions.TEMPOROSS_BOSS, AutoEntityHiderConfig::temporossBoss),
		new RegionRule("Tempoross: Lobby", MinigameRegions.TEMPOROSS_LOBBY, AutoEntityHiderConfig::temporossLobby),
		new RegionRule("Guardians of the Rift", MinigameRegions.GUARDIANS_OF_THE_RIFT, AutoEntityHiderConfig::guardiansOfTheRift)
	);

	public static List<RegionRule> getAll()
	{
		return Collections.unmodifiableList(RULES);
	}
}
