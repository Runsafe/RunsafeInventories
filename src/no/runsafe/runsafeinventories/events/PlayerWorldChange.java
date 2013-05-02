package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.event.player.IPlayerChangedWorldEvent;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.event.player.RunsafePlayerChangedWorldEvent;
import no.runsafe.runsafeinventories.InventoryHandler;

public class PlayerWorldChange implements IPlayerChangedWorldEvent
{
	public PlayerWorldChange(InventoryHandler inventoryHandler)
	{
		this.inventoryHandler = inventoryHandler;
	}

	@Override
	public void OnPlayerChangedWorld(RunsafePlayerChangedWorldEvent event)
	{
		this.inventoryHandler.handlePostWorldChange(event.getPlayer());
	}

	private InventoryHandler inventoryHandler;
}
