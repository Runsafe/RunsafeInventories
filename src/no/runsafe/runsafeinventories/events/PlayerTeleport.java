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
		this.output.write("Detected teleport event: " + event.getPlayer().getName());
	}

	private IOutput output;
}
