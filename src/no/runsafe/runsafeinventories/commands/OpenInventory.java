package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafeAmbiguousPlayer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.runsafeinventories.InventoryViewer;
import no.runsafe.runsafeinventories.UniverseHandler;

import java.util.HashMap;

public class OpenInventory extends PlayerCommand
{
	public OpenInventory(InventoryViewer inventoryViewer, UniverseHandler universeHandler)
	{
		super("open", "Opens a players inventory", "runsafe.inventories.open", "player");
		this.inventoryViewer = inventoryViewer;
		this.universeHandler = universeHandler;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, HashMap<String, String> parameters)
	{
		return null;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, HashMap<String, String> parameters, String[] arguments)
	{
		RunsafePlayer target = RunsafeServer.Instance.getPlayer(parameters.get("player"));

		if (target instanceof RunsafeAmbiguousPlayer)
			return target.toString();

		if (arguments.length > 0)
		{
			String universeName = arguments[0];
			if (!this.universeHandler.universeExists(universeName) && !this.universeHandler.worldExists(universeName))
				return "&cThe universe/world you are looking for does not exist.";

			if (!this.inventoryViewer.viewUniverseInventory(executor, target, universeName))
				return "&cThat player does not have an inventory in that universe.";
		}
		else
		{
			if (!this.inventoryViewer.hasDefaultUniverse())
				return "&cNo default universe has been defined, please specify one.";

			if (!this.inventoryViewer.viewUniverseInventory(executor, target))
				return "&cThat player does not have an inventory in that universe.";
		}
		return null;
	}

	private InventoryViewer inventoryViewer;
	private UniverseHandler universeHandler;
}
