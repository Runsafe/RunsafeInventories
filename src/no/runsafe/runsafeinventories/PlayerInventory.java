package no.runsafe.runsafeinventories;

import no.runsafe.framework.api.player.IPlayer;

public class PlayerInventory
{
	public PlayerInventory(IPlayer player, String inventoryName)
	{
		this.inventoryName = inventoryName;
		this.inventoryString = player.getInventory().serialize();
		this.owner = player;
		this.experience = player.getXP();
		this.level = player.getLevel();
		this.foodLevel = player.getFoodLevel();
	}

	public PlayerInventory(IPlayer owner, String inventoryName, String inventory, int level, float experience, int foodLevel)
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

	public IPlayer getPlayer()
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
	private final IPlayer owner;
	private final String inventoryString;
	private final int level;
	private final float experience;
	private final int foodLevel;
}
