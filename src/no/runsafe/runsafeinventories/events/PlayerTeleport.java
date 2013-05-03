package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.event.player.RunsafePlayerTeleportEvent;
import no.runsafe.runsafeinventories.InventoryHandler;

public class PlayerTeleport implements IPlayerTeleportEvent
{
	public PlayerTeleport(InventoryHandler inventoryHandler, IOutput output)
	{
		this.inventoryHandler = inventoryHandler;
		this.output = output;
	}

	@Override
	public void OnPlayerTeleport(RunsafePlayerTeleportEvent event)
	{
		this.output.fine("Detected teleport event: " + event.getPlayer().getName());
		// Check if we're about to teleport to another world.
		if (!event.getTo().getWorld().getName().equals(event.getFrom().getWorld().getName()))
			this.inventoryHandler.handlePreWorldChange(event.getPlayer());
	}

	private InventoryHandler inventoryHandler;
	private IOutput output;
}
