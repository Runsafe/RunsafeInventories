package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.world.IWorldLoad;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.runsafeinventories.RegionInventoryHandler;
import no.runsafe.worldguardbridge.WorldGuardInterface;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
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
		List<String> inventoryRegions = new ArrayList<String>(regionInventoryHandler.getInventoryRegionsInWorld(world));
		if (inventoryRegions.size() < 2)
			return;

		// Obtain all of the region's rectangles and remove invalid regions.
		List<Rectangle2D> inventoryRectangles = new ArrayList<Rectangle2D>(inventoryRegions.size());
		for (Iterator<String> iterator = inventoryRegions.iterator(); iterator.hasNext();)
		{
			String regionName = iterator.next();
			Rectangle2D regionRectangle = worldGuard.getRectangle(world, regionName);
			if (regionRectangle != null)
				inventoryRectangles.add(regionRectangle);
			else
			{
				console.logWarning("Detected invalid or removed inventory region: " + regionName);
				iterator.remove();
			}
		}

		// Make sure the list size didn't drop too low.
		int listSize = inventoryRectangles.size();
		if (listSize < 2)
			return;

		// Check for overlapping regions.
		for (int i = 0; i < listSize; i++)
			for (int k = i + 1; k < listSize; k++)
				if (!inventoryRectangles.get(i).equals(inventoryRectangles.get(k)))
					if (inventoryRectangles.get(i).intersects(inventoryRectangles.get(k)))
						console.logWarning(
							"Overlapping inventory regions detected: " + inventoryRegions.get(i) + ", " + inventoryRegions.get(k)
						);
	}

	private final IConsole console;
	private final WorldGuardInterface worldGuard;
	private final RegionInventoryHandler regionInventoryHandler;
}
