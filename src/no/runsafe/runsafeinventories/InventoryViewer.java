package no.runsafe.runsafeinventories;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.inventory.RunsafeInventory;
import no.runsafe.framework.server.inventory.RunsafeInventoryType;
import no.runsafe.framework.server.player.RunsafePlayer;

public class InventoryViewer implements IConfigurationChanged
{
	public InventoryViewer(InventoryRepository repository)
	{
		this.repository = repository;
	}

	public boolean viewUniverseInventory(RunsafePlayer viewer, RunsafePlayer owner)
	{
		return this.viewUniverseInventory(viewer, owner, this.defaultUniverse);
	}

	public boolean viewUniverseInventory(RunsafePlayer viewer, RunsafePlayer owner, String universeName)
	{
		PlayerInventory inventoryData = this.repository.getInventory(owner, universeName);

		if (inventoryData == null)
			return false;

		RunsafeInventory inventory = RunsafeServer.Instance.createInventory(null, RunsafeInventoryType.PLAYER);
		inventory.unserialize(inventoryData.getInventoryString());
		viewer.openInventory(inventory);

		return true;
	}

	public boolean hasDefaultUniverse()
	{
		return (this.defaultUniverse != null);
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.defaultUniverse = configuration.getConfigValueAsString("defaultOpenInventoryUniverse");
	}

	private InventoryRepository repository;
	private String defaultUniverse;
}
