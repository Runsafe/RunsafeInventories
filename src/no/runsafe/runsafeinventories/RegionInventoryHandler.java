package no.runsafe.runsafeinventories;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.player.IPlayerCustomEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;
import no.runsafe.runsafeinventories.repositories.InventoryRegionRepository;
import no.runsafe.worldguardbridge.WorldGuardInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionInventoryHandler implements IConfigurationChanged, IPlayerCustomEvent
{
	/**
	 * Constructor for RegionInventoryHandler
	 * @param inventoryRegionRepository Inventory region repository instance.
	 * @param worldGuard
	 */
	public RegionInventoryHandler(InventoryRegionRepository inventoryRegionRepository, WorldGuardInterface worldGuard)
	{
		this.inventoryRegionRepository = inventoryRegionRepository;
		this.worldGuard = worldGuard;
	}

	/**
	 * Check if the player is currently in an inventory region.
	 * @param player The player to check for.
	 * @return The name of the region if one exists, otherwise null.
	 */
	public String getPlayerInventoryRegion(IPlayer player)
	{
		String worldName = player.getWorldName(); // Name of the world the player is in.
		List<String> regions = worldGuard.getApplicableRegions(player); // Get region list.

		// Check each region and see if we have an inventory associated with any.
		for (String region : regions)
			if (doesRegionHaveInventory(worldName, region))
				return region;

		return null;
	}

	/**
	 * Check if a region has a separate inventory associated with it.
	 * @param worldName Name of the world the region is in.
	 * @param regionName Name of the region to check.
	 * @return True if the region has it's own inventory associated with it.
	 */
	public boolean doesRegionHaveInventory(String worldName, String regionName)
	{
		return inventoryRegions.containsKey(worldName) && inventoryRegions.get(worldName).contains(regionName);
	}

	/**
	 * Add a region to the ignore list, preventing the next enter event from triggering an inventory change.
	 * @param player The player who the event should be ignored for.
	 * @param region The region which should be ignored.
	 */
	public void ignoreRegion(IPlayer player, String region)
	{
		// Add the region to the ignore list.
		ignoreEventRegions.add(getRegionKey(player, region));
	}

	/**
	 * Remove a region from the ignore list for a player, allowing it to trigger events again.
	 * @param player The player who the event should be pardoned for.
	 * @param region The region which should no longer be ignored.
	 */
	private void removeIgnoredRegion(IPlayer player, String region)
	{
		// Remove the region from the ignore list.
		ignoreEventRegions.remove(getRegionKey(player, region));
	}

	/**
	 * Check if entry events for a specific region are currently ignored.
	 * @param player The player to check the event for.
	 * @param region The region to check the event for.
	 * @return True if entry events for the specified region are being ignored.
	 */
	private boolean isRegionIgnored(IPlayer player, String region)
	{
		return ignoreEventRegions.contains(getRegionKey(player, region));
	}

	/**
	 * Present a single string representing a player and world specific region.
	 * @param player The player to associate this key with.
	 * @param region The region to associate this key with.
	 * @return A combined key for the player, world and region.
	 */
	private String getRegionKey(IPlayer player, String region)
	{
		return String.format("%s-%s-%s", player.getName(), player.getWorldName(), region);
	}

	/**
	 * Called when the configuration for this plug-in is changed.
	 * @param configuration Object for accessing this plug-ins configuration.
	 */
	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		inventoryRegions = inventoryRegionRepository.getInventoryRegions();
	}

	/**
	 * Called when a custom event is fired from within the framework.
	 * @param event Object presenting event related data.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void OnPlayerCustomEvent(RunsafeCustomEvent event)
	{
		if (event.getEvent().equals("region.enter"))
		{
			Map<String, String> data = (Map<String, String>) event.getData();
			IPlayer player = event.getPlayer();
			String region = data.get("region");

			if (!isRegionIgnored(player, region))
			{
				// We should update the players inventory for this region.
			}
			else
			{
				// The region is ignored, so let's remove it so next time it isn't ignored.
				removeIgnoredRegion(player, region);
			}
		}
	}

	private final InventoryRegionRepository inventoryRegionRepository;
	private List<String> ignoreEventRegions = new ArrayList<String>();
	private HashMap<String, List<String>> inventoryRegions = new HashMap<String, List<String>>();
	private final WorldGuardInterface worldGuard;
}
