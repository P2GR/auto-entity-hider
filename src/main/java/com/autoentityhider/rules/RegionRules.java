package com.autoentityhider.rules;

import com.autoentityhider.regions.*;
import com.autoentityhider.AutoEntityHiderConfig;

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
		new RegionRule("TOA: Warden (P1/P2)", TombsOfAmascutRegions.WARDEN_P1_P2, AutoEntityHiderConfig::toaWardenP1P2),
		new RegionRule("TOA: Warden (P3/P4)", TombsOfAmascutRegions.WARDEN_P3_P4, AutoEntityHiderConfig::toaWardenP3P4),
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
		new RegionRule("Player-owned House", PlayerOwnedHouseRegions.REGION_IDS, AutoEntityHiderConfig::playerOwnedHouse),
		new RegionRule("Gemstone Crab", GemstoneCrabRegions.REGION_IDS, AutoEntityHiderConfig::gemstoneCrab),

		// Minigames
		new RegionRule("Wintertodt: Boss", MinigameRegions.WINTERTODT_BOSS, AutoEntityHiderConfig::wintertodtBoss),
		new RegionRule("Wintertodt: Bank", MinigameRegions.WINTERTODT_BANK, AutoEntityHiderConfig::wintertodtBank),
		new RegionRule("Tempoross: Boss", MinigameRegions.TEMPOROSS_BOSS, AutoEntityHiderConfig::temporossBoss),
		new RegionRule("Tempoross: Lobby", MinigameRegions.TEMPOROSS_LOBBY, AutoEntityHiderConfig::temporossLobby),
		new RegionRule("Guardians of the Rift", GuardiansOfTheRiftRegions.REGION_IDS, AutoEntityHiderConfig::guardiansOfTheRift),

		// Soulwars
		new RegionRule("Soulwars: Lobby", SoulwarsRegions.LOBBY, AutoEntityHiderConfig::soulwarsLobby),
		new RegionRule("Soulwars: Arena", SoulwarsRegions.ARENA, AutoEntityHiderConfig::soulwarsArena),

		// Castle Wars
		new RegionRule("Castle Wars", MinigameRegions.CASTLE_WARS, AutoEntityHiderConfig::castleWars),

		// Trouble Brewing
		new RegionRule("Trouble Brewing", MinigameRegions.TROUBLE_BREWING, AutoEntityHiderConfig::troubleBrewing),

		// Blast Furnace
		new RegionRule("Blast Furnace", MinigameRegions.BLAST_FURNACE, AutoEntityHiderConfig::blastFurnace),

		// Moons of Peril
		new RegionRule("Moons: Blood Moon", MoonsOfPerilRegions.BLOOD_MOON, AutoEntityHiderConfig::moonsOfPerilBloodMoon),
		new RegionRule("Moons: Blue Moon", MoonsOfPerilRegions.BLUE_MOON, AutoEntityHiderConfig::moonsOfPerilBlueMoon),
		new RegionRule("Moons: Eclipse Moon", MoonsOfPerilRegions.ECLIPSE_MOON, AutoEntityHiderConfig::moonsOfPerilEclipseMoon),
		new RegionRule("Moons: Pre-Boss Lobby", MoonsOfPerilRegions.PRE_BOSS_LOBBY, AutoEntityHiderConfig::moonsOfPerilPreBossLobby),
		new RegionRule("Moons: Reward Chest", MoonsOfPerilRegions.REWARD_CHEST, AutoEntityHiderConfig::moonsOfPerilRewardChest)
	);

	public static List<RegionRule> getAll()
	{
		return Collections.unmodifiableList(RULES);
	}
}
