package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.player.IPlayerRespawn;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.runsafeinventories.InventoryHandler;

public class PlayerRespawn implements IPlayerRespawn
{
	public PlayerRespawn(InventoryHandler inventoryHandler, IOutput output)
	{
		this.inventoryHandler = inventoryHandler;
		this.output = output;
	}

	@Override
	public RunsafeLocation OnPlayerRespawn(RunsafePlayer player, RunsafeLocation location, boolean isBed)
	{
		this.output.fine("Got respawn event for " + player.getName());
		this.inventoryHandler.handlePostWorldChange(player);
		return location;
	}

	private InventoryHandler inventoryHandler;
	private IOutput output;
}
