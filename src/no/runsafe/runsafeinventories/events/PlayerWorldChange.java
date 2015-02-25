package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.event.player.IPlayerChangedWorldEvent;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerChangedWorldEvent;
import no.runsafe.runsafeinventories.InventoryHandler;
import no.runsafe.runsafeinventories.UniverseHandler;
import no.runsafe.worldguardbridge.WorldGuardInterface;

import java.util.List;

public class PlayerWorldChange implements IPlayerChangedWorldEvent
{
	public PlayerWorldChange(InventoryHandler inventoryHandler, UniverseHandler universeHandler, IConsole console, WorldGuardInterface worldGuard)
	{
		this.inventoryHandler = inventoryHandler;
		this.universeHandler = universeHandler;
		this.console = console;
		this.worldGuard = worldGuard;
	}

	@Override
	public void OnPlayerChangedWorld(RunsafePlayerChangedWorldEvent event)
	{
		console.logInformation("EVENT CAUGHT: Player Changing World (post)");

		IPlayer player = event.getPlayer();

		List<String> regions = worldGuard.getApplicableRegions(player);
		for (String region : regions)
			console.logInformation("Region: " + region);

		this.inventoryHandler.handlePostWorldChange(player);

		// Remove any buff effects if we're changing universe
		if (!event.getSourceWorld().isConnected(player.getWorld()))
			player.removeBuffs();
	}

	private final InventoryHandler inventoryHandler;
	private final UniverseHandler universeHandler;
	private final IConsole console;
	private final WorldGuardInterface worldGuard;
}
