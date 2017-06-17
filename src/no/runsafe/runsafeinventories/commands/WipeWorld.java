package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.WorldArgument;
import no.runsafe.runsafeinventories.InventoryHandler;
import no.runsafe.runsafeinventories.UniverseHandler;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WipeWorld extends ExecutableCommand
{
	public WipeWorld(InventoryHandler inventoryHandler, IScheduler scheduler, UniverseHandler universeHandler)
	{
		super(
			"wipeworld",
			"Removes all inventories from the database for a world's universe.",
			"runsafe.inventories.wipeworld",
			new WorldArgument(WORLD).require()
		);
		this.scheduler = scheduler;
		this.inventoryHandler = inventoryHandler;
		this.universeHandler = universeHandler;
	}

	private static final String WORLD = "worldName";

	@Override
	public String OnExecute(final ICommandExecutor executor, IArgumentList parameters)
	{
		IWorld world = parameters.getValue(WORLD);
		if (world == null)
			return ("&cInvalid world.");

		String universeName = world.getUniverse().getName();

		if (wipers.containsKey(executor)) // If the user is executing the command again, confirm deletion.
		{
			scheduler.cancelTask(wipers.get(executor));
			wipers.remove(executor);
			this.inventoryHandler.wipeUniverse(universeName);
			return String.format("Deleted all database inventories for the %s universe.", universeName);
		}
		else
		{
			wipers.put(executor, scheduler.startSyncTask(() ->
			{
				if (wipers.containsKey(executor))
				{
					executor.sendColouredMessage("&cCancelling universe inventory wipe attempt.");
					wipers.remove(executor);
				}
			}, 15));

			executor.sendMessage(String.format("Are you sure you want to wipe inventories for the universe %s? This cannot be undone.", universeName));
			executor.sendMessage(String.format("The universe %s contains the worlds: ", universeName));

			List<String> universeWorlds = universeHandler.GetWorlds(universeName);
			if (universeWorlds != null)
				for (String worldName : universeWorlds)
					executor.sendMessage(worldName);
			else
				executor.sendMessage(world.getName());
			return "Run the command again to confirm universe inventory wipe.";
		}
	}

	private final IScheduler scheduler;
	private final UniverseHandler universeHandler;
	private final ConcurrentHashMap<ICommandExecutor, Integer> wipers = new ConcurrentHashMap<>();
	private final InventoryHandler inventoryHandler;
}
