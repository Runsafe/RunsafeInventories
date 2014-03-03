package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.runsafeinventories.UniverseHandler;
import no.runsafe.runsafeinventories.repositories.TemplateRepository;

public class CreateTemplate extends PlayerCommand
{
	public CreateTemplate(TemplateRepository templateRepository, UniverseHandler universeHandler)
	{
		super("createtemplate", "Creates a default inventory template", "runsafe.inventories.templates.create", new UniverseArgument(universeHandler));
		this.templateRepository = templateRepository;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		String universe = parameters.get("universe");
		if (universe == null)
		{
			IWorld world = executor.getWorld();
			if (world != null)
				universe = world.getUniverse().getName();
		}
		if (universe == null)
			return "Unable to locate universe.";
		this.templateRepository.insertTemplate(universe, executor.getInventory());
		return String.format("&2Created default inventory for %s using your inventory.", universe);
	}

	private final TemplateRepository templateRepository;
}
