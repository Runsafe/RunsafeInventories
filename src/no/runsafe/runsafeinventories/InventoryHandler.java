package no.runsafe.runsafeinventories;

import no.runsafe.framework.api.event.player.IPlayerCustomEvent;
import no.runsafe.framework.api.log.IDebug;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;
import no.runsafe.runsafeinventories.repositories.InventoryRepository;
import no.runsafe.runsafeinventories.repositories.TemplateRepository;

import java.util.Map;

public class InventoryHandler implements IPlayerCustomEvent
{
	public InventoryHandler(InventoryRepository inventoryRepository, TemplateRepository templateRepository, IDebug output, RegionInventoryHandler regionInventoryHandler)
	{
		this.inventoryRepository = inventoryRepository;
		this.templateRepository = templateRepository;
		this.debugger = output;
		this.regionInventoryHandler = regionInventoryHandler;
	}

	public void saveInventory(IPlayer player)
	{
		String inventoryRegion = regionInventoryHandler.getPlayerInventoryRegion(player);
		String universeName = player.getWorld().getUniverse().getName();
		String inventoryName = inventoryRegion == null ?  universeName : universeName + "-" + inventoryRegion;

		saveInventory(player, inventoryName);
	}

	private void saveInventory(IPlayer player, String inventoryName)
	{
		debugger.debugFine("Saving inventory %s for %s", inventoryName, player.getName());
		inventoryRepository.saveInventory(new PlayerInventory(player, inventoryName));
	}

	public void handlePreWorldChange(IPlayer player)
	{
		this.saveInventory(player); // Save inventory
		this.wipeInventory(player);
	}

	public void wipeInventory(IPlayer player)
	{
		this.debugger.debugFine("Wiping inventory for %s", player.getName());
		player.getInventory().clear(); // Clear inventory
		player.setXP(0); // Remove all XP
		player.setLevel(0); // Remove all levels
		player.setFoodLevel(20);
	}

	public void handlePostWorldChange(IPlayer player)
	{
		String universeName = player.getWorld().getUniverse().getName();

		PlayerInventory inventory;
		String inventoryRegion = regionInventoryHandler.getPlayerInventoryRegion(player);

		if (inventoryRegion != null)
			inventory = inventoryRepository.getInventoryForRegion(player, inventoryRegion);
		else
			inventory = inventoryRepository.getInventory(player, universeName); // Get inventory

		// If we are null, the player had no stored inventory.
		if (inventory != null)
			setInventory(player, inventory);
		else
		{
			// Lets check if we can give them a template.
			this.templateRepository.setToTemplate(universeName, player.getInventory());
		}
	}

	/**
	 * Called when a custom event is fired from within the framework.
	 * @param event Object containing event related data.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void OnPlayerCustomEvent(RunsafeCustomEvent event)
	{
		String eventName = event.getEvent();
		if (eventName.startsWith("inventory.region."))
		{
			IPlayer player = event.getPlayer();
			Map<String, String> data = (Map<String, String>) event.getData();

			if (eventName.equals("inventory.region.enter"))
			{
				// ToDo: Load the player's inventory for this region.
			}
			else if (eventName.equals("inventory.region.exit"))
			{
				// ToDo: Save the player's inventory for this region.
				// Save the inventory for the region we left.
				//saveInventory(player, data.get("world") + "-" + data.get("region"));
			}
		}
	}

	/**
	 * Sets a player's inventory.
	 * Assumes it is already empty.
	 * Also sets their level, xp and saturation (food level).
	 * @param player User who's inventory to set.
	 * @param inventory Player inventory to give to the player.
	 *                  Null for an empty inventory.
	 */
	private void setInventory(IPlayer player, PlayerInventory inventory)
	{
		if (inventory != null)
		{
			this.debugger.debugFine("Settings inventory for %s to %s", player.getName(), inventory.getInventoryName());
			player.getInventory().unserialize(inventory.getInventoryString()); // Restore inventory
			player.setLevel(inventory.getLevel()); // Restore level
			player.setXP(inventory.getExperience()); // Restore experience
			player.setFoodLevel(inventory.getFoodLevel()); // Restore food level
			player.updateInventory();
		}
	}

	private final InventoryRepository inventoryRepository;
	private final TemplateRepository templateRepository;
	private final IDebug debugger;
	private final RegionInventoryHandler regionInventoryHandler;
}
