package no.runsafe.runsafeinventories;

import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.player.RunsafePlayer;

public class PlayerInventory
{
	public PlayerInventory(RunsafePlayer player)
	{
		this.inventoryName = player.getWorld().getName(); // TODO: Use universes
		this.inventoryString = player.getInventory().serialize();
		this.owner = player.getName();
		this.experience = player.getXP();
		this.level = player.getLevel();
	}

	public PlayerInventory(String owner, String inventoryName, String inventory, Integer level, Float experience)
	{
		this.owner = owner;
		this.inventoryName = inventoryName;
		this.inventoryString = inventory;
		this.level = level;
		this.experience = experience;
	}

	public String getInventoryName()
	{
		return this.inventoryName;
	}

	public String getPlayerName()
	{
		return this.owner;
	}

	public RunsafePlayer getPlayer()
	{
		return RunsafeServer.Instance.getPlayer(this.owner);
	}

	public String getInventoryString()
	{
		return this.inventoryString;
	}

	public Integer getLevel()
	{
		return this.level;
	}

	public Float getExperience()
	{
		return this.experience;
	}

	private String inventoryName;
	private String owner;
	private String inventoryString;
	private Integer level;
	private Float experience;
}
