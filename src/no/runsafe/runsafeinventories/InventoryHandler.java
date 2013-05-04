package no.runsafe.runsafeinventories;

import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.player.RunsafePlayer;

public class InventoryHandler
{
	public InventoryHandler(InventoryRepository inventoryRepository, UniverseHandler universeHandler, IOutput output)
	{
		this.inventoryRepository = inventoryRepository;
		this.universeHandler = universeHandler;
		this.output = output;
	}

	public void saveInventory(RunsafePlayer player)
	{
		this.output.fine("Running force save for %s in %s", player.getName(), this.universeHandler.getUniverseName(player.getWorld()));
		this.inventoryRepository.saveInventory(
				new PlayerInventory(player, this.universeHandler.getUniverseName(player.getWorld()))
		); // Save
	}

	public void handlePreWorldChange(RunsafePlayer player)
	{
		this.saveInventory(player); // Save inventory
		this.wipeInventory(player);
	}

	public void wipeInventory(RunsafePlayer player)
	{
		this.output.fine("Wiping inventory for " + player.getName());
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
			this.output.fine(String.format("Settings inventory for %s to %s", player.getName(), inventory.getInventoryName()));
			player.getInventory().unserialize(inventory.getInventoryString()); // Restore inventory
			player.setLevel(inventory.getLevel()); // Restore level
			player.setXP(inventory.getExperience()); // Restore experience
			player.updateInventory();
		}
	}

	private InventoryRepository inventoryRepository;
	private UniverseHandler universeHandler;
	private IOutput output;
}
