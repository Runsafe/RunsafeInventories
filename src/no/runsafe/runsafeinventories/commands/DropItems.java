package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.SelfOrOnlinePlayer;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class DropItems extends ExecutableCommand
{
	public DropItems(IServer server)
	{
		super("drop", "Causes a player to drop all of their items", "runsafe.inventories.drop", new SelfOrOnlinePlayer());
		this.server = server;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		IPlayer player = server.getPlayer(parameters.get("player"));
		if (player == null)
			return "&cThat player does not exist.";

		this.dropItems(player);
		if (executor instanceof IPlayer && executor.getName().equals(player.getName()))
			return "&cYour items have been dropped.";
		return "&2Caused " + player.getPrettyName() + "&2 to drop their items.";
	}

	private void dropItems(IPlayer player)
	{
		RunsafeInventory inventory = player.getInventory();

		for (RunsafeMeta itemStack : inventory.getContents())
		{
			inventory.remove(itemStack);
			player.getWorld().dropItem(player.getLocation(), itemStack);
		}

		player.updateInventory();
	}

	private final IServer server;
}
