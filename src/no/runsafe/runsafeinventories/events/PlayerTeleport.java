package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.event.player.IPlayerPortalEvent;
import no.runsafe.framework.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.RunsafeLocation;
import no.runsafe.framework.server.RunsafeWorld;
import no.runsafe.framework.server.event.player.RunsafePlayerPortalEvent;
import no.runsafe.framework.server.event.player.RunsafePlayerTeleportEvent;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.runsafeinventories.InventoryHandler;

public class PlayerTeleport implements IPlayerTeleportEvent, IPlayerPortalEvent
{
	public PlayerTeleport(InventoryHandler inventoryHandler, IOutput output)
	{
		this.inventoryHandler = inventoryHandler;
		this.output = output;
	}

	@Override
	public void OnPlayerTeleport(RunsafePlayerTeleportEvent event)
	{
		this.output.fine("Detected teleport event: " + event.getPlayer().getName());
		RunsafeLocation from = event.getFrom();
		RunsafeLocation to = event.getTo();
		this.checkTeleportEvent(to == null ? null : to.getWorld(), from == null ? null : from.getWorld(), event.getPlayer());
	}

	@Override
	public void OnPlayerPortalEvent(RunsafePlayerPortalEvent event)
	{
		this.output.fine("Detected portal event: " + event.getPlayer().getName());
		RunsafeLocation from = event.getFrom();
		RunsafeLocation to = event.getTo();
		this.checkTeleportEvent(to == null ? null : to.getWorld(), from == null ? null : from.getWorld(), event.getPlayer());
	}

	private void checkTeleportEvent(RunsafeWorld to, RunsafeWorld from, RunsafePlayer player)
	{
		if (to != null && from != null)
			if (!to.getName().equals(from.getName()))
				this.inventoryHandler.handlePreWorldChange(player);
	}

	private InventoryHandler inventoryHandler;
	private IOutput output;

}
