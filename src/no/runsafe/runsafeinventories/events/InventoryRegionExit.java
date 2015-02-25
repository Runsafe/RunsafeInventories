package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.player.IPlayer;

public class InventoryRegionExit extends InventoryRegionEvent
{
	public InventoryRegionExit(IPlayer player, String region)
	{
		super(player, region, "inventory.region.exit");
	}
}
