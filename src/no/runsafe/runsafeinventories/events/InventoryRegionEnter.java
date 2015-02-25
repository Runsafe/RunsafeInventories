package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.player.IPlayer;

public class InventoryRegionEnter extends InventoryRegionEvent
{
	public InventoryRegionEnter(IPlayer player, String region)
	{
		super(player, region, "inventory.region.enter");
	}
}
