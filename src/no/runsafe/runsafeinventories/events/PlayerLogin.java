package no.runsafe.runsafeinventories.events;

import no.runsafe.framework.api.event.player.IPlayerJoinEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerJoinEvent;
import no.runsafe.runsafeinventories.repositories.InventoryRepository;

public class PlayerLogin implements IPlayerJoinEvent
{
	public PlayerLogin(InventoryRepository inventoryRepository)
	{
		this.inventoryRepository = inventoryRepository;
	}

	@Override
	public void OnPlayerJoinEvent(RunsafePlayerJoinEvent event)
	{
		inventoryRepository.updatePlayerUniqueId(event.getPlayer());
	}

	private final InventoryRepository inventoryRepository;
}
