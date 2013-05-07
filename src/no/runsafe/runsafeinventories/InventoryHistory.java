package no.runsafe.runsafeinventories;

import no.runsafe.framework.server.inventory.RunsafeInventory;

import java.util.HashMap;

public class InventoryHistory
{
	public void save(String playerName, RunsafeInventory inventory)
	{
		this.history.put(playerName, inventory);
	}

	public RunsafeInventory get(String playerName)
	{
		if (this.history.containsKey(playerName))
			return this.history.get(playerName);

		return null;
	}

	private HashMap<String, RunsafeInventory> history = new HashMap<String, RunsafeInventory>();
}
