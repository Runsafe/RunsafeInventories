package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.SelfOrAnyPlayer;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.runsafeinventories.InventoryHistory;

public class RestoreInventory extends ExecutableCommand
{
	public RestoreInventory(InventoryHistory history, IServer server)
	{
		super(
			"restore", "Reverts the last inventory switch/deletion", "runsafe.inventories.restore",
			new SelfOrAnyPlayer()
		);
		this.history = history;
		this.server = server;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		IPlayer player = server.getPlayer(parameters.get("player"));
		if (player == null)
			return "&cThat player does not exist";

		if (!this.history.restore(player))
			return "&cThere is no stored restoration data for this players inventory.";

		player.updateInventory();
		return "The inventory for " + player.getPrettyName() + " has been restored.";
	}

	private final InventoryHistory history;
	private final IServer server;
}
