package com.autoentityhider;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.autoentityhider.rules.RegionRule;
import net.runelite.client.plugins.autoentityhider.rules.RegionRules;
import net.runelite.client.plugins.entityhider.EntityHiderConfig;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@PluginDescriptor(
	name = "Auto Entity Hider",
	description = "Automatically toggle Entity Hider settings in selected regions and minigames",
	tags = {"entity", "hider", "auto", "toa", "tob", "cox", "wintertodt", "ge"},
	configName = AutoEntityHiderPlugin.CONFIG_NAME
)
public class AutoEntityHiderPlugin extends Plugin
{
	public static final String CONFIG_NAME = "AutoEntityHider";

	private static final String ENTITY_HIDER_GROUP = EntityHiderConfig.GROUP;
	private static final String KEY_HIDE_OTHERS = "hidePlayers";
	private static final String KEY_HIDE_OTHERS_2D = "hidePlayers2D";
	private static final String KEY_HIDE_PARTY = "hidePartyMembers";
	private static final String KEY_HIDE_FRIENDS = "hideFriends";
	private static final String KEY_HIDE_FRIENDS_CHAT = "hideClanMates";
	private static final String KEY_HIDE_CLAN_CHAT = "hideClanChatMembers";
	private static final String KEY_HIDE_IGNORES = "hideIgnores";
	private static final String KEY_HIDE_LOCAL = "hideLocalPlayer";
	private static final String KEY_HIDE_LOCAL_2D = "hideLocalPlayer2D";
	private static final String KEY_HIDE_NPCS = "hideNPCs";
	private static final String KEY_HIDE_NPCS_2D = "hideNPCs2D";
	private static final String KEY_HIDE_BOATS = "hideWorldEntities";
	private static final String KEY_HIDE_PETS = "hidePets";
	private static final String KEY_HIDE_ATTACKERS = "hideAttackers";
	private static final String KEY_HIDE_PROJECTILES = "hideProjectiles";
	private static final String KEY_HIDE_DEAD_NPCS = "hideDeadNpcs";
	private static final String KEY_HIDE_THRALLS = "hideThralls";
	private static final String KEY_HIDE_RANDOM_EVENTS = "hideRandomEvents";

	private static final String[] ENTITY_HIDER_KEYS = {
		KEY_HIDE_OTHERS,
		KEY_HIDE_OTHERS_2D,
		KEY_HIDE_PARTY,
		KEY_HIDE_FRIENDS,
		KEY_HIDE_FRIENDS_CHAT,
		KEY_HIDE_CLAN_CHAT,
		KEY_HIDE_IGNORES,
		KEY_HIDE_LOCAL,
		KEY_HIDE_LOCAL_2D,
		KEY_HIDE_NPCS,
		KEY_HIDE_NPCS_2D,
		KEY_HIDE_BOATS,
		KEY_HIDE_PETS,
		KEY_HIDE_ATTACKERS,
		KEY_HIDE_PROJECTILES,
		KEY_HIDE_DEAD_NPCS,
		KEY_HIDE_THRALLS,
		KEY_HIDE_RANDOM_EVENTS
	};

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ConfigManager configManager;

	@Inject
	private AutoEntityHiderConfig config;

	private final Map<String, String> priorEntityHiderValues = new HashMap<>();
	private final Set<String> priorEntityHiderUnset = new HashSet<>();
	private boolean autoApplied;
	private boolean snapshotCaptured;
	private boolean configDirty;
	private int lastRegionId = -1;
	private Boolean lastShouldApply;

	@Override
	protected void startUp()
	{
		clientThread.invoke(this::updateAutoEntityHider);
	}

	@Override
	protected void shutDown()
	{
		if (autoApplied)
		{
			restoreEntityHiderConfig();
		}
		autoApplied = false;
		snapshotCaptured = false;
	}

	@Provides
	AutoEntityHiderConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AutoEntityHiderConfig.class);
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		updateAutoEntityHider();
	}

	private void updateAutoEntityHider()
	{
		// Always active when the plugin is enabled.

		if (client.getLocalPlayer() == null)
		{
			return;
		}

		if (client.getGameState().getState() < GameState.LOGIN_SCREEN.getState())
		{
			return;
		}

		int currentRegionId = getCurrentRegionId();
		if (currentRegionId != lastRegionId)
		{
			lastRegionId = currentRegionId;
		}

		RegionRule matchedRule = findMatchingRule(currentRegionId);
		boolean shouldApply = matchedRule != null;

		if (lastShouldApply == null || shouldApply != lastShouldApply)
		{
			lastShouldApply = shouldApply;
		}

		if (shouldApply)
		{
			if (!snapshotCaptured)
			{
				snapshotEntityHiderConfig();
				snapshotCaptured = true;
			}

			if (configDirty || !autoApplied)
			{
				applyHideConfig();
				autoApplied = true;
				configDirty = false;
			}
		}
		else
		{
			if (autoApplied)
			{
				restoreEntityHiderConfig();
				autoApplied = false;
				snapshotCaptured = false;
			}
			configDirty = false;
		}
	}

	private int getCurrentRegionId()
	{
		if (client.getLocalPlayer() == null)
		{
			return -1;
		}
		return client.getLocalPlayer().getWorldLocation().getRegionID();
	}

	private RegionRule findMatchingRule(int currentRegionId)
	{
		for (RegionRule rule : RegionRules.getAll())
		{
			if (rule.isEnabled(config) && rule.getRegionId() == currentRegionId)
			{
				return rule;
			}
		}
		return null;
	}

	private void snapshotEntityHiderConfig()
	{
		priorEntityHiderValues.clear();
		priorEntityHiderUnset.clear();

		for (String key : ENTITY_HIDER_KEYS)
		{
			String value = configManager.getConfiguration(ENTITY_HIDER_GROUP, key);
			if (value == null)
			{
				priorEntityHiderUnset.add(key);
			}
			else
			{
				priorEntityHiderValues.put(key, value);
			}
		}
	}

	private void applyHideConfig()
	{
		applyAndLog(KEY_HIDE_OTHERS, config.hideOthers());
		applyAndLog(KEY_HIDE_OTHERS_2D, config.hideOthers2D());
		applyAndLog(KEY_HIDE_PARTY, config.hidePartyMembers());
		applyAndLog(KEY_HIDE_FRIENDS, config.hideFriends());
		applyAndLog(KEY_HIDE_FRIENDS_CHAT, config.hideFriendsChatMembers());
		applyAndLog(KEY_HIDE_CLAN_CHAT, config.hideClanChatMembers());
		applyAndLog(KEY_HIDE_IGNORES, config.hideIgnores());
		applyAndLog(KEY_HIDE_LOCAL, config.hideLocalPlayer());
		applyAndLog(KEY_HIDE_LOCAL_2D, config.hideLocalPlayer2D());
		applyAndLog(KEY_HIDE_NPCS, config.hideNPCs());
		applyAndLog(KEY_HIDE_NPCS_2D, config.hideNPCs2D());
		applyAndLog(KEY_HIDE_BOATS, config.hideWorldEntities());
		applyAndLog(KEY_HIDE_PETS, config.hidePets());
		applyAndLog(KEY_HIDE_ATTACKERS, config.hideAttackers());
		applyAndLog(KEY_HIDE_PROJECTILES, config.hideProjectiles());
		applyAndLog(KEY_HIDE_DEAD_NPCS, config.hideDeadNpcs());
		applyAndLog(KEY_HIDE_THRALLS, config.hideThralls());
		applyAndLog(KEY_HIDE_RANDOM_EVENTS, config.hideRandomEvents());
	}

	private void applyAndLog(String key, boolean value)
	{
		try
		{
			configManager.setConfiguration(ENTITY_HIDER_GROUP, key, Boolean.toString(value));
		}
		catch (RuntimeException ex)
		{
			log.error("Failed to set Entity Hider config [{}]={}", key, value, ex);
		}
	}

	private void restoreEntityHiderConfig()
	{
		if (priorEntityHiderValues.isEmpty() && priorEntityHiderUnset.isEmpty())
		{
			log.warn("Restore requested without a snapshot; skipping restore.");
			return;
		}

		for (String key : ENTITY_HIDER_KEYS)
		{
			try
			{
				if (priorEntityHiderUnset.contains(key))
				{
					configManager.unsetConfiguration(ENTITY_HIDER_GROUP, key);
				}
				else
				{
					String value = priorEntityHiderValues.get(key);
					if (value != null)
					{
						configManager.setConfiguration(ENTITY_HIDER_GROUP, key, value);
					}
					else
					{
						log.warn("Restore [{}]: no snapshot value found!", key);
					}
				}
			}
			catch (RuntimeException ex)
			{
				log.error("Failed to restore Entity Hider config [{}]", key, ex);
			}
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals(AutoEntityHiderConfig.GROUP))
		{
			configDirty = true;
			clientThread.invoke(this::updateAutoEntityHider);
			return;
		}

		if (event.getGroup().equals(ENTITY_HIDER_GROUP) && !autoApplied)
		{
			configDirty = true;
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.STARTING && autoApplied)
		{
			restoreEntityHiderConfig();
			autoApplied = false;
			snapshotCaptured = false;
		}
	}
}
