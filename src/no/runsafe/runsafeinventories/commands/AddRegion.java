package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.argument.WorldArgument;
import no.runsafe.runsafeinventories.RegionInventoryHandler;

public class AddRegion extends ExecutableCommand
{
	public AddRegion(RegionInventoryHandler regionInventoryHandler)
	{
		super(
			"addregion",
			"Add a custom inventory to a region.",
			"runsafe.inventories.region.add",
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
		if (regionInventoryHandler.addInventoryRegion(((IWorld) parameters.getValue(WORLD)).getName(), parameters.getValue(REGION)))
			return "Inventory region added.";
		return "&cInventory region could not be added.";
	}

	private final RegionInventoryHandler regionInventoryHandler;
}
