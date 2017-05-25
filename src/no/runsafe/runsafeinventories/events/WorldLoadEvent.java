package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.world.IWorldLoad;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.runsafeinventories.RegionInventoryHandler;
import no.runsafe.worldguardbridge.WorldGuardInterface;

import java.awt.geom.Rectangle2D;
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
			Rectangle2D regionRectangle = worldGuard.getRectangle(world, regionName);
			if (regionRectangle == null) // Check for invalid/removed regions.
				console.logWarning("Invalid or removed region: " + regionName);
			else
			{
				// Check for overlapping inventory regions.
				List<String> allInventoryRegions = regionInventoryHandler.getInventoryRegionsInWorld(world);
				if (allInventoryRegions.size() > 1)
					for (String region : allInventoryRegions)
						if (!regionName.equals(region) && regionRectangle.intersects(worldGuard.getRectangle(world, region)))
							console.logWarning ("Overlapping inventory regions detected: " + regionName + ", " + region);
			}
		}
	}

	private final IConsole console;
	private final WorldGuardInterface worldGuard;
	private final RegionInventoryHandler regionInventoryHandler;
}
