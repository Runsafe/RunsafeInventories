package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;

import java.util.HashMap;
import java.util.Map;

public abstract class InventoryRegionEvent extends RunsafeCustomEvent
{
	protected InventoryRegionEvent(IPlayer player, String region, String event)
	{
		super(player, event);
		data.put("world", player.getWorldName());
		data.put("region", region);
	}

	@Override
	public Object getData()
	{
		return data;
	}

	private final Map<String, String> data = new HashMap<String, String>();
}
