package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.event.player.IPlayerChangedWorldEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerChangedWorldEvent;
import no.runsafe.runsafeinventories.InventoryHandler;
import no.runsafe.runsafeinventories.RegionInventoryHandler;

public class PlayerWorldChange implements IPlayerChangedWorldEvent
{
	public PlayerWorldChange(InventoryHandler inventoryHandler, RegionInventoryHandler regionInventoryHandler)
	{
		this.inventoryHandler = inventoryHandler;
		this.regionInventoryHandler = regionInventoryHandler;
	}

	@Override
	public void OnPlayerChangedWorld(RunsafePlayerChangedWorldEvent event)
	{
		IPlayer player = event.getPlayer();

		regionInventoryHandler.blacklistCurrentRegionsEntry(player); // Blacklist region entry events.
		inventoryHandler.handlePostWorldChange(player); // Handle the world post-change.

		// Remove any buff effects if we're changing universe
		if (!event.getSourceWorld().isConnected(player.getWorld()))
			player.removeBuffs();
	}

	private final InventoryHandler inventoryHandler;
	private final RegionInventoryHandler regionInventoryHandler;
}
