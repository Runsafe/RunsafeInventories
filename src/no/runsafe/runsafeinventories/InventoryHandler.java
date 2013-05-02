package no.runsafe.runsafeinventories;

import no.runsafe.framework.server.player.RunsafePlayer;

public class InventoryHandler
{
	public InventoryHandler(InventoryRepository inventoryRepository, UniverseHandler universeHandler)
	{
		this.inventoryRepository = inventoryRepository;
		this.universeHandler = universeHandler;
	}

	public void saveInventory(RunsafePlayer player)
	{
		this.inventoryRepository.saveInventory(
				new PlayerInventory(player, this.universeHandler.getUniverseName(player.getWorld()))
		); // Save
	}

	public void handlePreWorldChange(RunsafePlayer player)
	{
		this.saveInventory(player); // Save inventory
		player.getInventory().clear(); // Clear inventory
		player.setXP(0); // Remove all XP
		player.setLevel(0); // Remove all levels
	}

	public void handlePostWorldChange(RunsafePlayer player)
	{
		PlayerInventory inventory = this.inventoryRepository.getInventory(player); // Get inventory

		// If we are null, the player had no stored inventory.
		if (inventory != null)
		{
			player.getInventory().unserialize(inventory.getInventoryString()); // Restore inventory
			player.setLevel(inventory.getLevel()); // Restore level
			player.setXP(inventory.getExperience()); // Restore experience
		}
	}

	private InventoryRepository inventoryRepository;
	private UniverseHandler universeHandler;
}
