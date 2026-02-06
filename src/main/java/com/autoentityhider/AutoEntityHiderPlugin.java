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
	private boolean configDirty;

	@Override
	protected void startUp()
	{
		log.info("AutoEntityHider starting up");
		entityHider.register();
		updateAutoEntityHider();
	}

	@Override
	protected void shutDown()
	{
		log.info("AutoEntityHider shutting down");
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
			log.debug("Region changed: {} -> {}", lastRegionId, currentRegionId);
			lastRegionId = currentRegionId;
		}

		RegionRule matchedRule = findMatchingRule(currentRegionId);
		boolean shouldApply = matchedRule != null;

		boolean stateChanged = (lastShouldApply == null || shouldApply != lastShouldApply);
		if (stateChanged)
		{
			log.info("AutoEntityHider decision changed: shouldApply={}, rule={}", shouldApply,
					matchedRule != null ? matchedRule.getName() : "none");
			lastShouldApply = shouldApply;
		}

		if (!stateChanged && !configDirty)
		{
			return;
		}

		if (shouldApply)
		{
			entityHider.applyInsideConfig(config);
			log.debug("Applied inside-region hiding for: {}", matchedRule.getName());
		}
		else
		{
			entityHider.applyOutsideConfig(config);
			log.debug("Applied outside-region hiding");
		}

		configDirty = false;
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
			log.debug("AutoEntityHider config changed: {}", event.getKey());
			configDirty = true;
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
			configDirty = false;
		}
	}
}
