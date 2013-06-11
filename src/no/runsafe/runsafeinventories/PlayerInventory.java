package no.runsafe.runsafeinventories;

import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class PlayerInventory
{
	public PlayerInventory(RunsafePlayer player, String inventoryName)
	{
		this.inventoryName = inventoryName;
		this.inventoryString = player.getInventory().serialize();
		this.owner = player.getName();
		this.experience = player.getXP();
		this.level = player.getLevel();
	}

	public PlayerInventory(String owner, String inventoryName, String inventory, int level, float experience)
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

	public String getInventoryString()
	{
		return this.inventoryString;
	}

	public int getLevel()
	{
		return this.level;
	}

	public float getExperience()
	{
		return this.experience;
	}

	private String inventoryName;
	private String owner;
	private String inventoryString;
	private int level;
	private float experience;
}
