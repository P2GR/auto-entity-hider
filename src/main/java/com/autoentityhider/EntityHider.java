/*
 * Copyright (c) 2018, Lotto <https://github.com/devLotto>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.autoentityhider;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import net.runelite.api.*;
import net.runelite.api.gameval.NpcID;
import net.runelite.api.gameval.SpotanimID;
import net.runelite.client.callback.RenderCallback;
import net.runelite.client.callback.RenderCallbackManager;
import net.runelite.client.game.NpcUtil;
import net.runelite.client.party.PartyService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;


@Singleton
public class EntityHider
{
	private static final Set<Integer> THRALL_IDS = ImmutableSet.of(
			NpcID.ARCEUUS_THRALL_GHOST_LESSER, NpcID.ARCEUUS_THRALL_SKELETON_LESSER, NpcID.ARCEUUS_THRALL_ZOMBIE_LESSER,
			NpcID.ARCEUUS_THRALL_GHOST_SUPERIOR, NpcID.ARCEUUS_THRALL_SKELETON_SUPERIOR, NpcID.ARCEUUS_THRALL_ZOMBIE_SUPERIOR,
			NpcID.ARCEUUS_THRALL_GHOST_GREATER, NpcID.ARCEUUS_THRALL_SKELETON_GREATER, NpcID.ARCEUUS_THRALL_ZOMBIE_GREATER
	);

	private static final Set<Integer> RANDOM_EVENT_NPC_IDS = ImmutableSet.of(
			NpcID.MACRO_BEEKEEPER_INVITATION,
			NpcID.MACRO_COMBILOCK_PIRATE,
			NpcID.MACRO_JEKYLL, NpcID.MACRO_JEKYLL_UNDERWATER,
			NpcID.MACRO_DWARF,
			NpcID.PATTERN_INVITATION,
			NpcID.MACRO_EVIL_BOB_OUTSIDE, NpcID.MACRO_EVIL_BOB_PRISON,
			NpcID.PINBALL_INVITATION,
			NpcID.MACRO_FORESTER_INVITATION,
			NpcID.MACRO_FROG_CRIER, NpcID.MACRO_FROG_GENERIC, NpcID.MACRO_FROG_SULKING, NpcID.MACRO_FROG_NONCOMBAT, NpcID.MACRO_FROG_NOHAT, NpcID.MACRO_FROG_PRIN_HE, NpcID.MACRO_FROG_PRIN_SHE, NpcID.MACRO_FROG_PRIN_A, NpcID.MACRO_FROG_PRIN_B,
			NpcID.MACRO_GENI, NpcID.MACRO_GENI_UNDERWATER,
			NpcID.MACRO_GILES, NpcID.MACRO_GILES_UNDERWATER,
			NpcID.MACRO_GRAVEDIGGER_INVITATION,
			NpcID.MACRO_MILES, NpcID.MACRO_MILES_UNDERWATER,
			NpcID.MACRO_MYSTERIOUS_OLD_MAN, NpcID.MACRO_MYSTERIOUS_OLD_MAN_UNDERWATER,
			NpcID.MACRO_MAZE_INVITATION, NpcID.MACRO_MIME_INVITATION,
			NpcID.MACRO_NILES, NpcID.MACRO_NILES_UNDERWATER,
			NpcID.MACRO_PILLORY_GUARD,
			NpcID.GRAB_POSTMAN,
			NpcID.MACRO_MAGNESON_INVITATION,
			NpcID.MACRO_HIGHWAYMAN, NpcID.MACRO_HIGHWAYMAN_UNDERWATER,
			NpcID.MACRO_SANDWICH_LADY_NPC,
			NpcID.MACRO_DRILLDEMON_INVITATION,
			NpcID.MACRO_COUNTCHECK_SURFACE, NpcID.MACRO_COUNTCHECK_UNDERWATER
	);

	@Inject
	private Client client;

	@Inject
	private RenderCallbackManager renderCallbackManager;

	@Inject
	private NpcUtil npcUtil;

	@Inject
	private PartyService partyService;

	private final RenderCallback renderCallback = new RenderCallback()
	{
		@Override
		public boolean addEntity(Renderable renderable, boolean ui)
		{
			return shouldDraw(renderable, ui);
		}
	};

	// Hide flags - controlled by AutoEntityHiderPlugin
	private boolean active;
	private boolean hideOthers;
	private boolean hideOthers2D;
	private boolean hidePartyMembers;
	private boolean hideFriends;
	private boolean hideFriendsChatMembers;
	private boolean hideClanMembers;
	private boolean hideIgnoredPlayers;
	private boolean hideLocalPlayer;
	private boolean hideLocalPlayer2D;
	private boolean hideNPCs;
	private boolean hideNPCs2D;
	private boolean hideBoats;
	private boolean hideDeadNpcs;
	private boolean hidePets;
	private boolean hideThralls;
	private boolean hideRandomEvents;
	private boolean hideAttackers;
	private boolean hideProjectiles;

	/**
	 * Register the draw listener with hooks. Call on plugin startup.
	 */
	public void register()
	{
		renderCallbackManager.register(renderCallback);
	}

	/**
	 * Unregister the draw listener from hooks. Call on plugin shutdown.
	 */
	public void unregister()
	{
		renderCallbackManager.unregister(renderCallback);
	}

	/**
	 * Apply hide settings from the config. Called when entering a configured region.
	 */
	public void applyInsideConfig(AutoEntityHiderConfig config)
	{
		applyFromConfig(config, false);
	}

	public void applyOutsideConfig(AutoEntityHiderConfig config)
	{
		applyFromConfig(config, true);
	}

	private void applyFromConfig(AutoEntityHiderConfig config, boolean outside)
	{
		this.active = true;
		this.hideOthers = outside ? config.outsideHideOthers() : config.hideOthers();
		this.hideOthers2D = outside ? config.outsideHideOthers2D() : config.hideOthers2D();
		this.hidePartyMembers = outside ? config.outsideHidePartyMembers() : config.hidePartyMembers();
		this.hideFriends = outside ? config.outsideHideFriends() : config.hideFriends();
		this.hideFriendsChatMembers = outside ? config.outsideHideFriendsChatMembers() : config.hideFriendsChatMembers();
		this.hideClanMembers = outside ? config.outsideHideClanChatMembers() : config.hideClanChatMembers();
		this.hideIgnoredPlayers = outside ? config.outsideHideIgnores() : config.hideIgnores();
		this.hideLocalPlayer = outside ? config.outsideHideLocalPlayer() : config.hideLocalPlayer();
		this.hideLocalPlayer2D = outside ? config.outsideHideLocalPlayer2D() : config.hideLocalPlayer2D();
		this.hideNPCs = outside ? config.outsideHideNPCs() : config.hideNPCs();
		this.hideNPCs2D = outside ? config.outsideHideNPCs2D() : config.hideNPCs2D();
		this.hideDeadNpcs = outside ? config.outsideHideDeadNpcs() : config.hideDeadNpcs();
		this.hideBoats = outside ? config.outsideHideWorldEntities() : config.hideWorldEntities();
		this.hidePets = outside ? config.outsideHidePets() : config.hidePets();
		this.hideThralls = outside ? config.outsideHideThralls() : config.hideThralls();
		this.hideRandomEvents = outside ? config.outsideHideRandomEvents() : config.hideRandomEvents();
		this.hideAttackers = outside ? config.outsideHideAttackers() : config.hideAttackers();
		this.hideProjectiles = outside ? config.outsideHideProjectiles() : config.hideProjectiles();
	}

	/**
	 * Clear all hide settings. Called when leaving a configured region.
	 */
	public void clearConfig()
	{
		this.active = false;
		this.hideOthers = false;
		this.hideOthers2D = false;
		this.hidePartyMembers = false;
		this.hideFriends = false;
		this.hideFriendsChatMembers = false;
		this.hideClanMembers = false;
		this.hideIgnoredPlayers = false;
		this.hideLocalPlayer = false;
		this.hideLocalPlayer2D = false;
		this.hideNPCs = false;
		this.hideNPCs2D = false;
		this.hideDeadNpcs = false;
		this.hideBoats = false;
		this.hidePets = false;
		this.hideThralls = false;
		this.hideRandomEvents = false;
		this.hideAttackers = false;
		this.hideProjectiles = false;
	}

	public boolean isActive()
	{
		return active;
	}

	@VisibleForTesting
	boolean shouldDraw(Renderable renderable, boolean drawingUI)
	{
		if (!active)
		{
			return true;
		}

		Player local = client.getLocalPlayer();
		if (local == null)
		{
			return true;
		}

		if (renderable instanceof Player)
		{
			Player player = (Player) renderable;

			if (player.getName() == null)
			{
				return true;
			}

			if (player == local)
			{
				return !(drawingUI ? hideLocalPlayer2D : hideLocalPlayer);
			}

			if (hideAttackers && player.getInteracting() == local)
			{
				return false;
			}

			if (partyService.isInParty() && partyService.getMemberByDisplayName(player.getName()) != null)
			{
				return !hidePartyMembers;
			}
			if (player.isFriend())
			{
				return !hideFriends;
			}
			if (player.isFriendsChatMember())
			{
				return !hideFriendsChatMembers;
			}
			if (player.isClanMember())
			{
				return !hideClanMembers;
			}
			if (client.getIgnoreContainer().findByName(player.getName()) != null)
			{
				return !hideIgnoredPlayers;
			}

			return !(drawingUI ? hideOthers2D : hideOthers);
		}
		else if (renderable instanceof NPC)
		{
			NPC npc = (NPC) renderable;

			if (npc.getComposition().isFollower() && npc != client.getFollower())
			{
				return !hidePets;
			}

			if (npcUtil.isDying(npc) && hideDeadNpcs)
			{
				return false;
			}

			if (npc.getInteracting() == local)
			{
				boolean b = hideAttackers;
				if (hideNPCs2D || hideNPCs)
				{
					b &= drawingUI ? hideNPCs2D : hideNPCs;
				}
				return !b;
			}

			if (THRALL_IDS.contains(npc.getId()))
			{
				return !hideThralls;
			}

			if (RANDOM_EVENT_NPC_IDS.contains(npc.getId()))
			{
				return !hideRandomEvents;
			}

			return !(drawingUI ? hideNPCs2D : hideNPCs);
		}
		else if (renderable instanceof Projectile)
		{
			return !hideProjectiles;
		}
		else if (renderable instanceof GraphicsObject)
		{
			if (!hideDeadNpcs)
			{
				return true;
			}

			switch (((GraphicsObject) renderable).getId())
			{
				case SpotanimID.TOB_NYLOCAS_DEATH_MELEE_STANDARD:
				case SpotanimID.TOB_NYLOCAS_DEATH_RANGED_STANDARD:
				case SpotanimID.TOB_NYLOCAS_DEATH_MAGIC_STANDARD:
				case SpotanimID.TOB_NYLOCAS_DEATH_MELEE_DETONATE:
				case SpotanimID.TOB_NYLOCAS_DEATH_RANGED_DETONATE:
				case SpotanimID.TOB_NYLOCAS_DEATH_MAGIC_DETONATE:
					return false;
				default:
					return true;
			}
		}
		else if (renderable instanceof Scene)
		{
			if (!hideBoats)
			{
				return true;
			}

			Scene scene = (Scene) renderable;
			WorldEntity we = client.getTopLevelWorldView().worldEntities().byIndex(scene.getWorldViewId());
			return we.getOwnerType() != WorldEntity.OWNER_TYPE_OTHER_PLAYER;
		}

		return true;
	}
}
