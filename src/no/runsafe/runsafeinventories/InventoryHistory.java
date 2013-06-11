package no.runsafe.runsafeinventories;

import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.HashMap;

public class InventoryHistory
{
	public void save(RunsafePlayer player)
	{
		this.history.put(player.getName(), player.getInventory().serialize());
	}

	public boolean restore(RunsafePlayer player)
	{
		if (this.history.containsKey(player.getName()))
		{
			player.getInventory().unserialize(this.history.get(player.getName()));
			return true;
		}
		return false;
	}

	private HashMap<String, String> history = new HashMap<String, String>();
}
