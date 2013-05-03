package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.server.event.player.RunsafePlayerTeleportEvent;
import no.runsafe.runsafeinventories.InventoryHandler;
import no.runsafe.runsafeinventories.UniverseHandler;

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
		String sourceUniverse = this.universeHandler.getUniverseName(event.getFrom().getWorld());
		String targetUniverse = this.universeHandler.getUniverseName(event.getTo().getWorld());

		if (!sourceUniverse.equals(targetUniverse))
			this.inventoryHandler.handlePreWorldChange(event.getPlayer());
	}

	private InventoryHandler inventoryHandler;
	private UniverseHandler universeHandler;
}
