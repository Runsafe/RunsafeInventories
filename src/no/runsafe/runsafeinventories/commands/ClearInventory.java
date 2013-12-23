package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.player.IAmbiguousPlayer;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.runsafeinventories.InventoryHistory;

import java.util.Map;

public class ClearInventory extends ExecutableCommand
{
	public ClearInventory(InventoryHistory history, IServer server)
	{
		super("clear", "Clears a players inventory", "runsafe.inventories.clear", new PlayerArgument(false));
		this.history = history;
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
				if (player instanceof IAmbiguousPlayer)
					return player.toString();

				if (player.isOnline())
				{
					this.history.save(player);
					player.getInventory().clear();
					player.updateInventory();
					return "&2Inventory for " + player.getPrettyName() + " &2cleared.";
				}
				return "&cThat player is offline.";
			}
			return "&cThat player does not exist.";
		}
		else
		{
			if (executor instanceof IPlayer)
			{
				IPlayer player = (IPlayer) executor;
				this.history.save(player);
				player.getInventory().clear();
				player.updateInventory();
				return "&2Your inventory has been cleared.";
			}
			return "&cPlease specify the player you wish to clear.";
		}
	}

	private final InventoryHistory history;
	private final IServer server;
}
