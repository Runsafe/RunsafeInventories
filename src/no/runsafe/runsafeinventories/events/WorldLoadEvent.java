package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.world.IWorldLoad;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.runsafeinventories.RegionInventoryHandler;
import no.runsafe.worldguardbridge.WorldGuardInterface;

import java.util.List;

public class WorldLoadEvent implements IWorldLoad
{
	public WorldLoadEvent(IConsole console, RegionInventoryHandler regionInventoryHandler, WorldGuardInterface worldGuard)
	{
		this.console = console;
		this.worldGuard = worldGuard;
		this.regionInventoryHandler = regionInventoryHandler;
	}

	public void OnWorldLoad(IWorld world)
	{
		// Check if any inventory regions are overlapping or are invalid.
		List<String> inventoryRegions = regionInventoryHandler.getInventoryRegionsInWorld(world);
		for(String regionName : inventoryRegions)
		{
			ILocation regionLocation = worldGuard.getRegionLocation(world, regionName);
			if (regionLocation == null) // Check for invalid/removed regions.
				console.logWarning("Invalid or removed region: " + regionName);
			else
			{
				// Check for overlapping inventory regions.
				List<String> regionsAtTargetLocation = worldGuard.getRegionsAtLocation(regionLocation);
				if (regionsAtTargetLocation.size() != 1)
					for (String overlappingRegion : regionsAtTargetLocation)
						if (!regionName.equals(overlappingRegion) && regionInventoryHandler.doesRegionHaveInventory(world.getName(), overlappingRegion))
							console.logWarning ("Overlapping inventory regions detected: " + regionName + ", " + overlappingRegion);
			}
		}
	}

	private final IConsole console;
	private final WorldGuardInterface worldGuard;
	private final RegionInventoryHandler regionInventoryHandler;
}
