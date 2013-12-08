package no.runsafe.runsafeinventories;

import no.runsafe.framework.api.player.IPlayer;

public class PlayerInventory
{
	public PlayerInventory(IPlayer player, String inventoryName)
	{
		this.inventoryName = inventoryName;
		this.inventoryString = player.getInventory().serialize();
		this.owner = player.getName();
		this.experience = player.getXP();
		this.level = player.getLevel();
		this.foodLevel = player.getFoodLevel();
	}

	public PlayerInventory(String owner, String inventoryName, String inventory, int level, float experience, int foodLevel)
	{
		this.owner = owner;
		this.inventoryName = inventoryName;
		this.inventoryString = inventory;
		this.level = level;
		this.experience = experience;
		this.foodLevel = foodLevel;
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

	public int getFoodLevel()
	{
		return this.foodLevel;
	}

	private final String inventoryName;
	private final String owner;
	private final String inventoryString;
	private final int level;
	private final float experience;
	private final int foodLevel;
}
