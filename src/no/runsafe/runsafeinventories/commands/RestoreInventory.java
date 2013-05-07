package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.command.ExecutableCommand;
import no.runsafe.framework.server.ICommandExecutor;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.player.RunsafeAmbiguousPlayer;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.runsafeinventories.InventoryHistory;

import java.util.HashMap;

public class RestoreInventory extends ExecutableCommand
{
	public RestoreInventory(InventoryHistory history)
	{
		super("restoreinventory", "Reverts the last inventory switch/deletion", "runsafe.inventories.restore", "player");
		this.history = history;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, HashMap<String, String> parameters)
	{
		RunsafePlayer player = RunsafeServer.Instance.getPlayer(parameters.get("player"));
		if (player == null)
			return "&cThat player does not exist";

		if (player instanceof RunsafeAmbiguousPlayer)
			return player.toString();

		if (!this.history.restore(player))
			return "&cThere is no stored restoration data for this players inventory.";

		player.updateInventory();
		return "The inventory for " + player.getPrettyName() + " has been restored.";
	}

	private InventoryHistory history;
}
