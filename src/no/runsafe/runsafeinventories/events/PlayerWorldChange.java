package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.event.player.IPlayerChangedWorldEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerChangedWorldEvent;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.runsafeinventories.InventoryHandler;
import no.runsafe.runsafeinventories.UniverseHandler;

public class PlayerWorldChange implements IPlayerChangedWorldEvent
{
	public PlayerWorldChange(InventoryHandler inventoryHandler, UniverseHandler universeHandler)
	{
		this.inventoryHandler = inventoryHandler;
		this.universeHandler = universeHandler;
	}

	@Override
	public void OnPlayerChangedWorld(RunsafePlayerChangedWorldEvent event)
	{
		RunsafePlayer player = event.getPlayer();
		this.inventoryHandler.handlePostWorldChange(player);

		// Remove any buff effects if we're changing universe
		if (!universeHandler.getUniverseName(event.getSourceWorld()).equals(universeHandler.getUniverseName(player.getWorld())))
			player.removeBuffs();
	}

	private InventoryHandler inventoryHandler;
	private UniverseHandler universeHandler;
}
