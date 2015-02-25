package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.event.player.IPlayerChangedWorldEvent;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerChangedWorldEvent;
import no.runsafe.runsafeinventories.InventoryHandler;
import no.runsafe.runsafeinventories.RegionInventoryHandler;
import no.runsafe.runsafeinventories.UniverseHandler;
import no.runsafe.worldguardbridge.WorldGuardInterface;

import java.util.List;

public class PlayerWorldChange implements IPlayerChangedWorldEvent
{
	public PlayerWorldChange(InventoryHandler inventoryHandler, UniverseHandler universeHandler, WorldGuardInterface worldGuard, RegionInventoryHandler regionInventoryHandler)
	{
		this.inventoryHandler = inventoryHandler;
		this.universeHandler = universeHandler;
		this.worldGuard = worldGuard;
		this.regionInventoryHandler = regionInventoryHandler;
	}

	@Override
	public void OnPlayerChangedWorld(RunsafePlayerChangedWorldEvent event)
	{
		IPlayer player = event.getPlayer();

		// Loop all current regions and ignore them.
		for (String region : worldGuard.getApplicableRegions(player))
			regionInventoryHandler.ignoreRegion(player, region);

		this.inventoryHandler.handlePostWorldChange(player);

		// Remove any buff effects if we're changing universe
		if (!event.getSourceWorld().isConnected(player.getWorld()))
			player.removeBuffs();
	}

	private final InventoryHandler inventoryHandler;
	private final UniverseHandler universeHandler;
	private final WorldGuardInterface worldGuard;
	private final RegionInventoryHandler regionInventoryHandler;
}
