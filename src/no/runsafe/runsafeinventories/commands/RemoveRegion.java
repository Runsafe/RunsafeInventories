package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.argument.WorldArgument;
import no.runsafe.runsafeinventories.RegionInventoryHandler;

import java.util.concurrent.ConcurrentHashMap;

public class RemoveRegion extends ExecutableCommand
{
	public RemoveRegion(RegionInventoryHandler regionInventoryHandler, IScheduler scheduler)
	{
		super(
			"removeregion",
			"Removes an inventory region and wipes its inventory data.",
			"runsafe.inventories.region.remove",
			new WorldArgument(WORLD).require(),
			new RequiredArgument(REGION)
		);
		this.regionInventoryHandler = regionInventoryHandler;
		this.scheduler = scheduler;
	}

	private static final String WORLD = "world";
	private static final String REGION = "region";

	@Override
	public String OnExecute(final ICommandExecutor executor, IArgumentList parameters)
	{
		IWorld world = parameters.getValue(WORLD);
		if (world == null)
			return ("&cInvalid world.");

		String worldName = world.getName();
		String regionName = parameters.getValue(REGION);

		if (wipers.containsKey(executor)) // If the user is executing the command again, confirm deletion.
		{
			scheduler.cancelTask(wipers.get(executor));
			wipers.remove(executor);
			if (regionInventoryHandler.removeInventoryRegion(worldName, regionName))
				return String.format("Inventory region %s removed.", regionName);
			return String.format("&cInventory region %s could not be removed.", regionName);
		}
		else
		{
			wipers.put(executor, scheduler.startSyncTask(new Runnable()
			{
				@Override
				public void run()
				{
					if (wipers.containsKey(executor))
					{
						executor.sendColouredMessage("&cCancelling remove inventory region attempt.");
						wipers.remove(executor);
					}
				}
			}, 15));

			executor.sendMessage(
				String.format(
					"Are you sure you want to delete the inventory region %s in %s? This cannot be undone.",
					regionName, worldName
				)
			);
			return "Run the command again to confirm inventory region deletion.";
		}
	}

	private final IScheduler scheduler;
	private final ConcurrentHashMap<ICommandExecutor, Integer> wipers = new ConcurrentHashMap<>();
	private final RegionInventoryHandler regionInventoryHandler;
}
