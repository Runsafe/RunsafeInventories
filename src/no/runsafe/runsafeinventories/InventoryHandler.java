package no.runsafe.runsafeinventories;

import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.runsafeinventories.repositories.InventoryRepository;
import no.runsafe.runsafeinventories.repositories.TemplateRepository;

public class InventoryHandler
{
	public InventoryHandler(InventoryRepository inventoryRepository, UniverseHandler universeHandler, TemplateRepository templateRepository, IOutput output)
	{
		this.inventoryRepository = inventoryRepository;
		this.universeHandler = universeHandler;
		this.templateRepository = templateRepository;
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
		String universeName = this.universeHandler.getUniverseName(player.getWorld().getName());
		PlayerInventory inventory = this.inventoryRepository.getInventory(player, universeName); // Get inventory

		// If we are null, the player had no stored inventory.
		if (inventory != null)
		{
			this.output.fine(String.format("Settings inventory for %s to %s", player.getName(), inventory.getInventoryName()));
			player.getInventory().unserialize(inventory.getInventoryString()); // Restore inventory
			player.setLevel(inventory.getLevel()); // Restore level
			player.setXP(inventory.getExperience()); // Restore experience
			player.updateInventory();
		}
		else
		{
			// Lets check if we can give them a template.
			this.templateRepository.setToTemplate(universeName, player.getInventory());
		}
	}

	private InventoryRepository inventoryRepository;
	private UniverseHandler universeHandler;
	private TemplateRepository templateRepository;

	private IOutput output;
}
