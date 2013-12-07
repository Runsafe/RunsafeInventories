package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.IDebug;
import no.runsafe.framework.api.event.player.IPlayerRespawn;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.runsafeinventories.InventoryHandler;

public class PlayerRespawn implements IPlayerRespawn
{
	public PlayerRespawn(InventoryHandler inventoryHandler, IDebug debugger)
	{
		this.inventoryHandler = inventoryHandler;
		this.debugger = debugger;
	}

	@Override
	public RunsafeLocation OnPlayerRespawn(IPlayer player, RunsafeLocation location, boolean isBed)
	{
		this.debugger.debugFine("Got respawn event for %s", player.getName());
		this.inventoryHandler.handlePostWorldChange(player);
		return location;
	}

	private InventoryHandler inventoryHandler;
	private IDebug debugger;
}
