package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.event.player.IPlayerQuitEvent;
import no.runsafe.framework.server.event.player.RunsafePlayerQuitEvent;
import no.runsafe.runsafeinventories.InventoryHandler;

public class PlayerQuit implements IPlayerQuitEvent
{
	public PlayerQuit(InventoryHandler inventoryHandler)
	{
		this.inventoryHandler = inventoryHandler;
	}

	@Override
	public void OnPlayerQuit(RunsafePlayerQuitEvent event)
	{
		this.inventoryHandler.saveInventory(event.getPlayer());
	}

	private InventoryHandler inventoryHandler;
}
