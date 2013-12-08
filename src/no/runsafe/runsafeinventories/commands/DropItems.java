package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.player.RunsafeAmbiguousPlayer;

import java.util.Map;

public class DropItems extends ExecutableCommand
{
	public DropItems(IServer server)
	{
		super("drop", "Causes a player to drop all of their items", "runsafe.inventories.drop", new PlayerArgument(false));
		this.server = server;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, Map<String, String> parameters)
	{
		if (parameters.containsKey("player"))
		{
			IPlayer player = server.getPlayer(parameters.get("player"));
			if (player != null)
			{
				if (player instanceof RunsafeAmbiguousPlayer)
					return player.toString();

				if (player.isOnline())
				{
					this.dropItems(player);
					return "&2Caused " + player.getPrettyName() + "&2 to drop their items.";
				}
				return "&cThat player is not online.";
			}
			return "&cThat player does not exist.";
		}
		else
		{
			if (executor instanceof IPlayer)
			{
				this.dropItems((IPlayer) executor);
				return "&cYour items have been dropped.";
			}
			return "&cPlease specify a player.";
		}
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
