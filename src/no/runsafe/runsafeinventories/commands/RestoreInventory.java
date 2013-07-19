package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafeAmbiguousPlayer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.runsafeinventories.InventoryHistory;

import java.util.Map;

public class RestoreInventory extends ExecutableCommand
{
	public RestoreInventory(InventoryHistory history)
	{
		super("restore", "Reverts the last inventory switch/deletion", "runsafe.inventories.restore", "player");
		this.history = history;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, Map<String, String> parameters)
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
