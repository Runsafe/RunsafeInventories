package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.Player;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class DropItems extends ExecutableCommand
{
	public DropItems()
	{
		super(
			"drop",
			"Causes a player to drop all of their items",
			"runsafe.inventories.drop",
			new Player().onlineOnly().defaultToExecutor()
		);
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		IPlayer player = parameters.getValue("player");
		if (player == null)
			return "&cInvalid player.";

		this.dropItems(player);
		if (executor instanceof IPlayer && executor.equals(player))
			return "&cYour items have been dropped.";
		return "&2Caused " + player.getPrettyName() + "&2 to drop their items.";
	}

	private void dropItems(IPlayer player)
	{
		RunsafeInventory inventory = player.getInventory();
		ILocation location = player.getLocation();
		if (location == null)
			return;

		for (RunsafeMeta itemStack : inventory.getContents())
		{
			inventory.remove(itemStack);
			location.getWorld().dropItem(location, itemStack);
		}
		player.updateInventory();
	}
}
