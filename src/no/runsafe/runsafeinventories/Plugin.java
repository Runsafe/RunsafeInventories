package no.runsafe.runsafeinventories;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.runsafeinventories.commands.*;
import no.runsafe.runsafeinventories.events.*;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{
		// Handlers
		this.addComponent(InventoryHandler.class);
		this.addComponent(UniverseHandler.class);
		this.addComponent(InventoryHistory.class);

		// Repositories
		this.addComponent(InventoryRepository.class);

		// Commands
		this.addComponent(WipeWorld.class);
		this.addComponent(ClearInventory.class);
		this.addComponent(DropItems.class);
		this.addComponent(SwitchInventory.class);
		this.addComponent(RestoreInventory.class);

		// Events
		this.addComponent(PlayerTeleport.class);
		this.addComponent(PlayerWorldChange.class);
		this.addComponent(PlayerQuit.class);
		this.addComponent(PlayerRespawn.class);
		this.addComponent(PlayerDeath.class);
	}
}
