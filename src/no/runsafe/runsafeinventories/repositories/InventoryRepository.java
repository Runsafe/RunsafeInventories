package no.runsafe.runsafeinventories.repositories;

import no.runsafe.framework.api.database.*;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.runsafeinventories.PlayerInventory;

public class InventoryRepository extends Repository
{
	public InventoryRepository(IDatabase database)
	{
		this.database = database;
	}

	@Override
	public String getTableName()
	{
		return "runsafeInventories";
	}

	public void saveInventory(PlayerInventory inventory)
	{
		database.execute(
			"INSERT INTO runsafeInventories (owner, inventoryName, inventory, level, experience, foodLevel) VALUES(?, ?, ?, ?, ?, ?)" +
				" ON DUPLICATE KEY UPDATE inventory = ?, level = ?, experience = ?, foodLevel = ?",
			inventory.getPlayerName(), inventory.getInventoryName(),
			inventory.getInventoryString(), inventory.getLevel(), inventory.getExperience(), inventory.getFoodLevel(),
			inventory.getInventoryString(), inventory.getLevel(), inventory.getExperience(), inventory.getFoodLevel()
		);
	}

	public PlayerInventory getInventory(IPlayer player, String universeName)
	{
		String owner = player.getName();

		IRow data = database.queryRow(
			"SELECT inventory, level, experience, foodLevel FROM runsafeInventories WHERE owner = ? AND inventoryName = ?",
			owner, universeName
		);

		if (data.isEmpty())
			return null; // We have no inventory, so no need to return a blank one.

		long level = data.Long("level");

		return new PlayerInventory(
			owner,
			universeName,
			data.String("inventory"),
			(int) level,
			data.Float("experience"),
			data.Integer("foodLevel")
		);
	}

	public void wipeWorld(String worldName)
	{
		database.execute("DELETE FROM runsafeInventories WHERE inventoryName = ?", worldName);
	}

	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate update = new SchemaUpdate();

		update.addQueries(
			"CREATE TABLE `runsafeInventories` (" +
				"`owner` varchar(50) NOT NULL," +
				"`inventoryName` varchar(255) NOT NULL," +
				"`inventory` longtext," +
				"`level` int(10) unsigned NOT NULL DEFAULT '0'," +
				"`experience` float unsigned NOT NULL DEFAULT '0'," +
				"PRIMARY KEY (`owner`,`inventoryName`)" +
			")"
		);

		update.addQueries("ALTER TABLE `runsafeInventories`" +
				"ADD COLUMN `foodLevel` TINYINT(2) UNSIGNED NOT NULL DEFAULT '20' AFTER `experience`");

		return update;
	}

	private final IDatabase database;
}
