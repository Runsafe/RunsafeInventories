package no.runsafe.runsafeinventories;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.api.command.Command;
import no.runsafe.framework.features.*;
import no.runsafe.runsafeinventories.commands.*;
import no.runsafe.runsafeinventories.events.*;
import no.runsafe.runsafeinventories.repositories.InventoryRepository;
import no.runsafe.runsafeinventories.repositories.TemplateRepository;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void pluginSetup()
	{
		// Framework features
		addComponent(Commands.class);
		addComponent(Events.class);
		addComponent(Database.class);
		addComponent(UniverseRegistration.class);

		// Handlers
		this.addComponent(InventoryHandler.class);
		this.addComponent(UniverseHandler.class);
		this.addComponent(InventoryHistory.class);
		this.addComponent(InventoryViewer.class);

		// Repositories
		this.addComponent(InventoryRepository.class);
		this.addComponent(TemplateRepository.class);

		// Commands
		this.addComponent(WipeWorld.class);

		Command inventoryCommand = new Command("inventory", "Inventory handling commands", null);
		inventoryCommand.addSubCommand(getInstance(DropItems.class));
		inventoryCommand.addSubCommand(getInstance(SwitchInventory.class));
		inventoryCommand.addSubCommand(getInstance(OpenInventory.class));
		inventoryCommand.addSubCommand(getInstance(RestoreInventory.class));
		inventoryCommand.addSubCommand(getInstance(CreateTemplate.class));
		inventoryCommand.addSubCommand(getInstance(ClearInventory.class));
		this.addComponent(inventoryCommand);

		// Events
		this.addComponent(PlayerTeleport.class);
		this.addComponent(PlayerWorldChange.class);
		this.addComponent(PlayerQuit.class);
		this.addComponent(PlayerRespawn.class);
		this.addComponent(PlayerDeath.class);
	}
}
