package com.autoentityhider;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import com.autoentityhider.rules.RegionRule;
import com.autoentityhider.rules.RegionRules;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
		name = "Auto Entity Hider",
		description = "Automatically toggle Entity Hider settings in selected regions and minigames",
		tags = {"entity", "hider", "auto", "toa", "tob", "cox", "wintertodt", "ge", "region"},
		conflicts = "Entity Hider"
)
public class AutoEntityHiderPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private AutoEntityHiderConfig config;

	@Inject
	private EntityHider entityHider;

	private int lastRegionId = -1;
	private Boolean lastShouldApply;

	@Override
	protected void startUp()
	{
		entityHider.register();
		lastRegionId = -1;
		lastShouldApply = null;
		updateAutoEntityHider();
	}

	@Override
	protected void shutDown()
	{
		entityHider.clearConfig();
		entityHider.unregister();
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

			if (shouldApply)
			{
				entityHider.applyConfig(config);
			}
			else
			{
				entityHider.clearConfig();
			}
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

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals(AutoEntityHiderConfig.GROUP))
		{
			updateAutoEntityHider();
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.STARTING)
		{
			entityHider.clearConfig();
			lastShouldApply = null;
			return;
		}

		if (event.getGameState() == GameState.LOGGED_IN)
		{
			updateAutoEntityHider();
		}
	}
}
