package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.WorldArgument;
import no.runsafe.runsafeinventories.repositories.InventoryRepository;

public class WipeWorld extends ExecutableCommand
{
	public WipeWorld(InventoryRepository inventoryRepository)
	{
		super(
			"wipeworld", "Removes all inventories from the database for a world", "runsafe.inventories.wipeworld",
			new WorldArgument()
		);
		this.inventoryRepository = inventoryRepository;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		String worldName = parameters.get("worldName");
		this.inventoryRepository.wipeWorld(worldName);
		return String.format("Deleted all database inventories for the %s world", worldName);
	}

	private final InventoryRepository inventoryRepository;
}
