package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.WorldArgument;
import no.runsafe.runsafeinventories.InventoryHandler;

public class WipeWorld extends ExecutableCommand
{
	public WipeWorld(InventoryHandler inventoryHandler)
	{
		super(
			"wipeworld", "Removes all inventories from the database for a world's universe.", "runsafe.inventories.wipeworld",
			new WorldArgument()
		);
		this.inventoryHandler = inventoryHandler;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		IWorld world = parameters.getValue("worldName");
		if (world == null)
			return ("&cInvalid world.");

		String universeName = world.getUniverse().getName();
		this.inventoryHandler.wipeUniverse(universeName);
		return String.format("Deleted all database inventories for the %s universe.", universeName);
	}

	private final InventoryHandler inventoryHandler;
}
