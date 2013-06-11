package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.player.IPlayerDeathEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerDeathEvent;
import no.runsafe.runsafeinventories.InventoryHandler;

public class PlayerDeath implements IPlayerDeathEvent
{
	public PlayerDeath(InventoryHandler inventoryHandler, IOutput output)
	{
		this.inventoryHandler = inventoryHandler;
		this.output = output;
	}

	@Override
	public void OnPlayerDeathEvent(RunsafePlayerDeathEvent event)
	{
		this.output.fine("Detected death event for " + event.getEntity().getName());
		this.inventoryHandler.wipeInventory(event.getEntity());
		this.inventoryHandler.saveInventory(event.getEntity());
	}

	private InventoryHandler inventoryHandler;
	private IOutput output;
}
