package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.command.argument.ITabComplete;
import no.runsafe.framework.api.command.argument.OptionalArgument;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.runsafeinventories.UniverseHandler;

import java.util.List;

public class UniverseArgument extends OptionalArgument implements ITabComplete
{
	public UniverseArgument(UniverseHandler universeHandler)
	{
		super("universe");
		this.universeHandler = universeHandler;
	}

	@Override
	public List<String> getAlternatives(IPlayer executor, String partial)
	{
		return universeHandler.GetUniverses();
	}

	private final UniverseHandler universeHandler;
}
