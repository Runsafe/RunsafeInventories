package no.runsafe.runsafeinventories.repositories;

import no.runsafe.framework.api.IUniverse;
import no.runsafe.framework.api.database.IRow;
import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.runsafeinventories.PlayerInventory;

public class InventoryRepository extends Repository
{
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

	/**
	 * Retrieve a player inventory by its name.
	 * @param player The player to grab the inventory for.
	 * @param universeName The name identifying the inventory to retrieve.
	 * @return A player inventory object or null if none could be found.
	 */
	public PlayerInventory getInventory(IPlayer player, String universeName)
	{
		return getInventoryData(player, universeName);
	}

	/**
	 * Retrieve a player inventory for a specific region.
	 * @param player The player to grab the inventory for.
	 * @param regionName The region to grab the inventory for.
	 * @return A player inventory object or null if none could be found.
	 */
	public PlayerInventory getInventoryForRegion(IPlayer player, String regionName)
	{
		String regionKey = player.getWorldName(); // Grab the world name.

		// Check if we have a universe associated with this world.
		IUniverse playerUniverse = player.getUniverse();
		if (playerUniverse != null)
			regionKey = playerUniverse.getName(); // Use the universe name instead.

		// Append the region name to the end to create a unique key.
		regionKey = regionKey + "-" + regionName;

		return getInventoryData(player, regionKey);
	}

	private PlayerInventory getInventoryData(IPlayer player, String universeName)
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
}
