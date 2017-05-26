package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.event.player.IPlayerCustomEvent;
import no.runsafe.framework.api.log.IDebug;
import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;

public class PlayerRegionEvents implements IPlayerCustomEvent
{
	public PlayerRegionEvents(IDebug debugger)
	{
		this.debugger = debugger;
	}

	@Override
	public void OnPlayerCustomEvent(RunsafeCustomEvent event)
	{
		String eventName = event.getEvent();
		if (eventName.equals("inventory.region.enter") || eventName.equals("inventory.region.exit"))
			debugger.debugFine("EVENT CAUGHT: " + eventName + " FOR PLAYER : " + event.getPlayer().getPrettyName());
	}

	private final IDebug debugger;
}
