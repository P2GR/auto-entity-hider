package com.autoentityhider;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

public class AutoEntityHiderOverlay extends Overlay
{
	private final Client client;
	private final AutoEntityHiderConfig config;
	private final ModelOutlineRenderer modelOutlineRenderer;
	private final AutoEntityHiderPlugin plugin;

	@Inject
	public AutoEntityHiderOverlay(Client client, AutoEntityHiderConfig config,
		ModelOutlineRenderer modelOutlineRenderer, AutoEntityHiderPlugin plugin)
	{
		this.client = client;
		this.config = config;
		this.modelOutlineRenderer = modelOutlineRenderer;
		this.plugin = plugin;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
		setPriority(OverlayPriority.HIGH);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.playerOutline() || !plugin.isInConfiguredRegion())
		{
			return null;
		}

		if (client.getLocalPlayer() == null)
		{
			return null;
		}

		modelOutlineRenderer.drawOutline(client.getLocalPlayer(),
			config.playerOutlineBorderWidth(),
			config.playerOutlineColor(),
			config.playerOutlineFeather());
		return null;
	}
}
