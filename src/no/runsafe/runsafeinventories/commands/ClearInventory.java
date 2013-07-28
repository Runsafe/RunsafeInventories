package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafeAmbiguousPlayer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.runsafeinventories.InventoryHistory;

import java.util.Map;

public class ClearInventory extends ExecutableCommand
{
	public ClearInventory(InventoryHistory history)
	{
		super("clear", "Clears a players inventory", "runsafe.inventories.clear", new PlayerArgument(false));
		this.history = history;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, Map<String, String> parameters)
	{
		if (parameters.containsKey("player"))
		{
			RunsafePlayer player = RunsafeServer.Instance.getPlayer(parameters.get("player"));
			if (player != null)
			{
				if (player instanceof RunsafeAmbiguousPlayer)
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
			if (executor instanceof RunsafePlayer)
			{
				RunsafePlayer player = (RunsafePlayer) executor;
				this.history.save(player);
				player.getInventory().clear();
				player.updateInventory();
				return "&2Your inventory has been cleared.";
			}
			return "&cPlease specify the player you wish to clear.";
		}
	}

	private InventoryHistory history;
}
