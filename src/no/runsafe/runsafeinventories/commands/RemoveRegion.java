package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.argument.WorldArgument;
import no.runsafe.runsafeinventories.RegionInventoryHandler;

public class RemoveRegion extends ExecutableCommand
{
	public RemoveRegion(RegionInventoryHandler regionInventoryHandler)
	{
		super("removeregion",
			"Removes an inventory region.",
			"runsafe.inventories.region.remove",
			new WorldArgument(WORLD).require(),
			new RequiredArgument(REGION)
		);
		this.regionInventoryHandler = regionInventoryHandler;
	}

	private static final String WORLD = "world";
	private static final String REGION = "region";

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		regionInventoryHandler.removeInventoryRegion(((IWorld) parameters.getValue(WORLD)).getName(), parameters.getValue(REGION));
		return "Inventory region removed.";
	}

	private final RegionInventoryHandler regionInventoryHandler;
}
