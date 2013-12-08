package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.player.RunsafeAmbiguousPlayer;
import no.runsafe.runsafeinventories.InventoryViewer;
import no.runsafe.runsafeinventories.UniverseHandler;

import java.util.Map;

public class OpenInventory extends PlayerCommand
{
	public OpenInventory(InventoryViewer inventoryViewer, UniverseHandler universeHandler, IServer server)
	{
		super("open", "Opens a players inventory", "runsafe.inventories.open", new PlayerArgument(), new UniverseArgument(universeHandler));
		this.inventoryViewer = inventoryViewer;
		this.universeHandler = universeHandler;
		this.server = server;
	}

	@Override
	public String OnExecute(IPlayer executor, Map<String, String> parameters)
	{
		IPlayer target = server.getPlayer(parameters.get("player"));

		if (target instanceof RunsafeAmbiguousPlayer)
			return target.toString();

		if (parameters.containsKey("universe"))
		{
			String universeName = parameters.get("universe");
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

	private final InventoryViewer inventoryViewer;
	private final UniverseHandler universeHandler;
	private final IServer server;
}
