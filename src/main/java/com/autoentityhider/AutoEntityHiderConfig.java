package com.autoentityhider;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(AutoEntityHiderConfig.GROUP)
public interface AutoEntityHiderConfig extends Config
{
	String GROUP = "autoentityhider";

	@ConfigSection(
			name = "Locations",
			description = "General locations",
			position = 0
	)
	String locationsSection = "locations";

	@ConfigSection(
			name = "Chambers of Xeric",
			description = "Chambers of Xeric room toggles",
			position = 1
	)
	String coxSection = "cox";

	@ConfigSection(
			name = "Theatre of Blood",
			description = "Theatre of Blood room toggles",
			position = 2
	)
	String tobSection = "tob";

	@ConfigSection(
			name = "Tombs of Amascut",
			description = "Tombs of Amascut room toggles",
			position = 3
	)
	String toaSection = "toa";

	@ConfigSection(
			name = "Minigames",
			description = "Minigame settings",
			position = 4
	)
	String minigamesSection = "minigames";

	@ConfigSection(
			name = "Hide Config",
			description = "Entity Hider flags to apply",
			position = 5
	)
	String hideSection = "hide";

	// ------------------------------
	// Locations
	// ------------------------------
	@ConfigItem(
			position = 10,
			keyName = "grandExchange",
			name = "Grand Exchange",
			description = "Apply hiding at the Grand Exchange",
			section = locationsSection
	)
	default boolean grandExchange()
	{
		return false;
	}

	// ------------------------------
	// Chambers of Xeric
	// ------------------------------
	@ConfigItem(
			position = 13,
			keyName = "coxOlm",
			name = "Olm",
			description = "Apply hiding in the Olm room",
			section = coxSection
	)
	default boolean coxOlm()
	{
		return true;
	}

	// ------------------------------
	// Tombs of Amascut
	// ------------------------------
	@ConfigItem(
			position = 21,
			keyName = "toaLobby",
			name = "Lobby",
			description = "Apply hiding in Tombs lobby",
			section = toaSection
	)
	default boolean toaLobby()
	{
		return true;
	}

	@ConfigItem(
			position = 22,
			keyName = "toaAkkhaPuzzleRoom",
			name = "Akkha: Puzzle Room",
			description = "Apply hiding in Akkha puzzle room",
			section = toaSection
	)
	default boolean toaAkkhaPuzzleRoom()
	{
		return true;
	}

	@ConfigItem(
			position = 23,
			keyName = "toaAkkhaBossRoom",
			name = "Akkha: Boss Room",
			description = "Apply hiding in Akkha boss room",
			section = toaSection
	)
	default boolean toaAkkhaBossRoom()
	{
		return true;
	}

	@ConfigItem(
			position = 24,
			keyName = "toaBabaPuzzleRoom",
			name = "Baba: Puzzle Room",
			description = "Apply hiding in Baba puzzle room",
			section = toaSection
	)
	default boolean toaBabaPuzzleRoom()
	{
		return true;
	}

	@ConfigItem(
			position = 25,
			keyName = "toaBabaBossRoom",
			name = "Baba: Boss Room",
			description = "Apply hiding in Baba boss room",
			section = toaSection
	)
	default boolean toaBabaBossRoom()
	{
		return true;
	}

	@ConfigItem(
			position = 26,
			keyName = "toaKephriPuzzleRoom",
			name = "Kephri: Puzzle Room",
			description = "Apply hiding in Kephri puzzle room",
			section = toaSection
	)
	default boolean toaKephriPuzzleRoom()
	{
		return true;
	}

	@ConfigItem(
			position = 27,
			keyName = "toaKephriBossRoom",
			name = "Kephri: Boss Room",
			description = "Apply hiding in Kephri boss room",
			section = toaSection
	)
	default boolean toaKephriBossRoom()
	{
		return true;
	}

	@ConfigItem(
			position = 28,
			keyName = "toaZebakPuzzleRoom",
			name = "Zebak: Puzzle Room",
			description = "Apply hiding in Zebak puzzle room",
			section = toaSection
	)
	default boolean toaZebakPuzzleRoom()
	{
		return true;
	}

	@ConfigItem(
			position = 29,
			keyName = "toaZebakBossRoom",
			name = "Zebak: Boss Room",
			description = "Apply hiding in Zebak boss room",
			section = toaSection
	)
	default boolean toaZebakBossRoom()
	{
		return true;
	}

	@ConfigItem(
			position = 30,
			keyName = "toaWardenObeliskRoom",
			name = "Warden: Obelisk Room",
			description = "Apply hiding in Warden obelisk room",
			section = toaSection
	)
	default boolean toaWardenObeliskRoom()
	{
		return true;
	}

	@ConfigItem(
			position = 31,
			keyName = "toaRewardRoom",
			name = "Reward Room",
			description = "Apply hiding in Tombs reward room",
			section = toaSection
	)
	default boolean toaRewardRoom()
	{
		return true;
	}

	// ------------------------------
	// Theatre of Blood
	// ------------------------------

	@ConfigItem(
			position = 17,
			keyName = "tobMaiden",
			name = "Maiden",
			description = "Apply hiding in Maiden",
			section = tobSection
	)
	default boolean tobMaiden()
	{
		return true;
	}

	@ConfigItem(
			position = 18,
			keyName = "tobBloat",
			name = "Bloat",
			description = "Apply hiding in Bloat",
			section = tobSection
	)
	default boolean tobBloat()
	{
		return true;
	}

	@ConfigItem(
			position = 19,
			keyName = "tobNylos",
			name = "Nylos",
			description = "Apply hiding in Nylocas",
			section = tobSection
	)
	default boolean tobNylos()
	{
		return true;
	}

	@ConfigItem(
			position = 20,
			keyName = "tobSotetseg",
			name = "Sotetseg",
			description = "Apply hiding in Sotetseg",
			section = tobSection
	)
	default boolean tobSotetseg()
	{
		return true;
	}

	@ConfigItem(
			position = 21,
			keyName = "tobXarpus",
			name = "Xarpus",
			description = "Apply hiding in Xarpus",
			section = tobSection
	)
	default boolean tobXarpus()
	{
		return true;
	}

	@ConfigItem(
			position = 22,
			keyName = "tobVerzik",
			name = "Verzik",
			description = "Apply hiding in Verzik",
			section = tobSection
	)
	default boolean tobVerzik()
	{
		return true;
	}

	@ConfigItem(
			position = 23,
			keyName = "tobReward",
			name = "Reward Room",
			description = "Apply hiding in Theatre reward room",
			section = tobSection
	)
	default boolean tobRewardRoom()
	{
		return true;
	}

	// ------------------------------
	// Minigames
	// ------------------------------
	@ConfigItem(
			position = 44,
			keyName = "wintertodtBoss",
			name = "Wintertodt: Boss Area",
			description = "Apply hiding in Wintertodt boss area",
			section = minigamesSection
	)
	default boolean wintertodtBoss()
	{
		return true;
	}

	@ConfigItem(
			position = 45,
			keyName = "wintertodtBank",
			name = "Wintertodt: Bank Area",
			description = "Apply hiding in Wintertodt bank area",
			section = minigamesSection
	)
	default boolean wintertodtBank()
	{
		return true;
	}

	@ConfigItem(
			position = 46,
			keyName = "temporossBoss",
			name = "Tempoross: Boss",
			description = "Apply hiding in Tempoross boss area",
			section = minigamesSection
	)
	default boolean temporossBoss()
	{
		return true;
	}

	@ConfigItem(
			position = 47,
			keyName = "temporossLobby",
			name = "Tempoross: Lobby",
			description = "Apply hiding in Tempoross lobby area",
			section = minigamesSection
	)
	default boolean temporossLobby()
	{
		return true;
	}

	@ConfigItem(
			position = 48,
			keyName = "guardiansOfTheRift",
			name = "Guardians of the Rift",
			description = "Apply hiding in Guardians of the Rift",
			section = minigamesSection
	)
	default boolean guardiansOfTheRift()
	{
		return true;
	}

	// ------------------------------
	// Hide Config
	// ------------------------------
	@ConfigItem(
			position = 60,
			keyName = "hideOthers",
			name = "Hide others",
			description = "Hide other players",
			section = hideSection
	)
	default boolean hideOthers()
	{
		return true;
	}

	@ConfigItem(
			position = 61,
			keyName = "hideOthers2D",
			name = "Hide others' 2D",
			description = "Hide other players' 2D elements",
			section = hideSection
	)
	default boolean hideOthers2D()
	{
		return true;
	}

	@ConfigItem(
			position = 62,
			keyName = "hidePartyMembers",
			name = "Hide party members",
			description = "Hide party members",
			section = hideSection
	)
	default boolean hidePartyMembers()
	{
		return false;
	}

	@ConfigItem(
			position = 63,
			keyName = "hideFriends",
			name = "Hide friends",
			description = "Hide friends",
			section = hideSection
	)
	default boolean hideFriends()
	{
		return false;
	}

	@ConfigItem(
			position = 64,
			keyName = "hideFriendsChatMembers",
			name = "Hide friends chat members",
			description = "Hide friends chat members",
			section = hideSection
	)
	default boolean hideFriendsChatMembers()
	{
		return false;
	}

	@ConfigItem(
			position = 65,
			keyName = "hideClanChatMembers",
			name = "Hide clan chat members",
			description = "Hide clan chat members",
			section = hideSection
	)
	default boolean hideClanChatMembers()
	{
		return false;
	}

	@ConfigItem(
			position = 66,
			keyName = "hideIgnores",
			name = "Hide ignores",
			description = "Hide ignored players",
			section = hideSection
	)
	default boolean hideIgnores()
	{
		return false;
	}

	@ConfigItem(
			position = 67,
			keyName = "hideLocalPlayer",
			name = "Hide local player",
			description = "Hide the local player",
			section = hideSection
	)
	default boolean hideLocalPlayer()
	{
		return false;
	}

	@ConfigItem(
			position = 68,
			keyName = "hideLocalPlayer2D",
			name = "Hide local player 2D",
			description = "Hide the local player's 2D elements",
			section = hideSection
	)
	default boolean hideLocalPlayer2D()
	{
		return false;
	}

	@ConfigItem(
			position = 69,
			keyName = "hideNPCs",
			name = "Hide NPCs",
			description = "Hide NPCs",
			section = hideSection
	)
	default boolean hideNPCs()
	{
		return false;
	}

	@ConfigItem(
			position = 70,
			keyName = "hideNPCs2D",
			name = "Hide NPCs 2D",
			description = "Hide NPCs 2D elements",
			section = hideSection
	)
	default boolean hideNPCs2D()
	{
		return false;
	}

	@ConfigItem(
			position = 71,
			keyName = "hideWorldEntities",
			name = "Hide boats",
			description = "Hide boats and related world entities",
			section = hideSection
	)
	default boolean hideWorldEntities()
	{
		return false;
	}

	@ConfigItem(
			position = 72,
			keyName = "hidePets",
			name = "Hide others' pets",
			description = "Hide other players' pets",
			section = hideSection
	)
	default boolean hidePets()
	{
		return false;
	}

	@ConfigItem(
			position = 73,
			keyName = "hideAttackers",
			name = "Hide attackers",
			description = "Hide NPCs/players attacking you",
			section = hideSection
	)
	default boolean hideAttackers()
	{
		return false;
	}

	@ConfigItem(
			position = 74,
			keyName = "hideProjectiles",
			name = "Hide projectiles",
			description = "Hide projectiles",
			section = hideSection
	)
	default boolean hideProjectiles()
	{
		return false;
	}

	@ConfigItem(
			position = 75,
			keyName = "hideDeadNpcs",
			name = "Hide dead NPCs",
			description = "Hide dead NPCs",
			section = hideSection
	)
	default boolean hideDeadNpcs()
	{
		return false;
	}

	@ConfigItem(
			position = 76,
			keyName = "hideThralls",
			name = "Hide thralls",
			description = "Hide thralls",
			section = hideSection
	)
	default boolean hideThralls()
	{
		return false;
	}

	@ConfigItem(
			position = 77,
			keyName = "hideRandomEvents",
			name = "Hide random events",
			description = "Hide other players' random events",
			section = hideSection
	)
	default boolean hideRandomEvents()
	{
		return false;
	}
}
