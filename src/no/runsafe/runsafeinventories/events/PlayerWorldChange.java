package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.event.player.IPlayerChangedWorldEvent;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.event.player.RunsafePlayerChangedWorldEvent;

public class PlayerWorldChange implements IPlayerChangedWorldEvent
{
	public PlayerWorldChange(IOutput output)
	{
		this.output = output;
	}

	@Override
	public void OnPlayerChangedWorld(RunsafePlayerChangedWorldEvent event)
	{
		this.output.write(String.format("World Change detected. Player: %s. SourceWorld: %s", event.getPlayer().getName(), event.getSourceWorld().getName()));
	}

	private IOutput output;
}
