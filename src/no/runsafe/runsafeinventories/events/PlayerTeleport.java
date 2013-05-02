package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.event.player.RunsafePlayerTeleportEvent;
import no.runsafe.runsafeinventories.InventoryHandler;

public class PlayerTeleport implements IPlayerTeleportEvent
{
	public PlayerTeleport(InventoryHandler inventoryHandler)
	{
		this.inventoryHandler = inventoryHandler;
	}

	@Override
	public void OnPlayerTeleport(RunsafePlayerTeleportEvent event)
	{
		// Check if we're about to teleport to another world.
		if (!event.getTo().getWorld().getName().equals(event.getFrom().getWorld().getName()))
			this.inventoryHandler.handlePreWorldChange(event.getPlayer());
	}

	private InventoryHandler inventoryHandler;
}
