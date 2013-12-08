package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.runsafeinventories.UniverseHandler;
import no.runsafe.runsafeinventories.repositories.TemplateRepository;

import java.util.Map;

public class CreateTemplate extends PlayerCommand
{
	public CreateTemplate(TemplateRepository templateRepository, UniverseHandler universeHandler)
	{
		super("createtemplate", "Creates a default inventory template", "runsafe.inventories.templates.create", new UniverseArgument(universeHandler));
		this.templateRepository = templateRepository;
	}

	@Override
	public String OnExecute(IPlayer executor, Map<String, String> parameters)
	{
		String universe = parameters.containsKey("universe") ? parameters.get("universe") : executor.getWorld().getUniverse().getName();
		this.templateRepository.insertTemplate(universe, executor.getInventory());
		return String.format("&2Created default inventory for %s using your inventory.", universe);
	}

	private final TemplateRepository templateRepository;
}
