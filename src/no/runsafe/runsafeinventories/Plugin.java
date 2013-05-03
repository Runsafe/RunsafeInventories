package no.runsafe.runsafeinventories;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.runsafeinventories.commands.WipeWorld;
import no.runsafe.runsafeinventories.events.PlayerQuit;
import no.runsafe.runsafeinventories.events.PlayerTeleport;
import no.runsafe.runsafeinventories.events.PlayerWorldChange;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{
		// Handlers
		this.addComponent(InventoryHandler.class);
		this.addComponent(UniverseHandler.class);

		// Repositories
		this.addComponent(InventoryRepository.class);

		// Commands
		this.addComponent(WipeWorld.class);

		// Events
		this.addComponent(PlayerTeleport.class);
		this.addComponent(PlayerWorldChange.class);
		this.addComponent(PlayerQuit.class);
	}
}
