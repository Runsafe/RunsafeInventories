package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.SelfOrOnlinePlayer;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.runsafeinventories.InventoryHistory;

public class ClearInventory extends ExecutableCommand
{
	public ClearInventory(InventoryHistory history, IServer server)
	{
		super("clear", "Clears a players inventory", "runsafe.inventories.clear", new SelfOrOnlinePlayer());
		this.history = history;
		this.server = server;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		IPlayer player = server.getPlayer(parameters.get("player"));
		if (player == null)
			return "Player not found.";
		this.history.save(player);
		player.getInventory().clear();
		player.updateInventory();
		if (executor instanceof IPlayer && executor.getName().equals(player.getName()))
			return "&2Your inventory has been cleared.";
		return "&2Inventory for " + player.getPrettyName() + " &2cleared.";
	}

	private final InventoryHistory history;
	private final IServer server;
}
