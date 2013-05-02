package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.event.player.RunsafePlayerTeleportEvent;

public class PlayerTeleport implements IPlayerTeleportEvent
{
	public PlayerTeleport(IOutput output)
	{
		this.output = output;
	}

	@Override
	public void OnPlayerTeleport(RunsafePlayerTeleportEvent event)
	{
		if (!event.getTo().getWorld().equals(event.getFrom().getWorld()))
		{
			this.output.write("We detect that " + event.getPlayer() + " is about to change world");
		}
	}

	private IOutput output;
}
