package no.runsafe.runsafeinventories;

import no.runsafe.framework.server.player.RunsafePlayer;

public class InventoryHandler
{
	public InventoryHandler(InventoryRepository inventoryRepository)
	{
		this.inventoryRepository = inventoryRepository;
	}

	public void handlePreWorldChange(RunsafePlayer player)
	{
		this.inventoryRepository.saveInventory(new PlayerInventory(player)); // Save
		player.getInventory().clear(); // Clear inventory
		player.setXP(0); // Remove all XP
		player.setLevel(0); // Remove all levels
	}

	public void handlePostWorldChange(RunsafePlayer player)
	{
		PlayerInventory inventory = this.inventoryRepository.getInventory(player); // Get inventory
		player.getInventory().unserialize(inventory.getInventoryString()); // Restore inventory
		player.setLevel(inventory.getLevel()); // Restore level
		player.setXP(inventory.getExperience()); // Restore experience
	}

	private InventoryRepository inventoryRepository;
}
