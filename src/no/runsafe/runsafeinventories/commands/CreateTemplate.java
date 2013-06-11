package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.runsafeinventories.UniverseHandler;
import no.runsafe.runsafeinventories.repositories.TemplateRepository;

import java.util.HashMap;

public class CreateTemplate extends PlayerCommand
{
	public CreateTemplate(TemplateRepository templateRepository, UniverseHandler universeHandler)
	{
		super("createtemplate", "Creates a default inventory template", "runsafe.inventories.templates.create");
		this.templateRepository = templateRepository;
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
		String universe = (arguments.length > 0 ? arguments[0] : universeHandler.getUniverseName(executor.getWorld()));
		this.templateRepository.insertTemplate(universe, executor.getInventory());

		return String.format("&2Created default inventory for %s using your inventory.", universe);
	}

	private TemplateRepository templateRepository;
	private UniverseHandler universeHandler;
}
