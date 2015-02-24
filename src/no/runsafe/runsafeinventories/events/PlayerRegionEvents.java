package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.event.player.IPlayerCustomEvent;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;

public class PlayerRegionEvents implements IPlayerCustomEvent
{
	public PlayerRegionEvents(IConsole console)
	{
		this.console = console;
	}

	@Override
	public void OnPlayerCustomEvent(RunsafeCustomEvent event)
	{
		String eventName = event.getEvent();
		if (eventName.equals("region.enter") || eventName.equals("region.leave"))
			console.logInformation("EVENT CAUGHT: " + eventName);
	}

	private final IConsole console;
}
