package no.runsafe.runsafeinventories.repositories;

import no.runsafe.framework.api.database.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryRegionRepository extends Repository
{
	@Nonnull
	@Override
	public String getTableName()
	{
		return "runsafe_inventories_regions";
	}

	@Override
	@Nonnull
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate update = new SchemaUpdate();

		update.addQueries(
			"CREATE TABLE `runsafe_inventories_regions` (" +
				"`worldName` varchar(50) NOT NULL, " +
				"`regionName` varchar(50) NOT NULL," +
				"PRIMARY KEY (`worldName`)" +
			")"
		);
		update.addQueries(String.format("ALTER TABLE %s DROP PRIMARY KEY", getTableName()));
		update.addQueries(String.format("ALTER TABLE %s ADD PRIMARY KEY (`worldName`, `regionName`)", getTableName()));

		return update;
	}

	/**
	 * Creates a new inventory region.
	 * @param worldName The world the region is in.
	 * @param regionName The region to add.
	 */
	public void addInventoryRegion(String worldName, String regionName)
	{
		database.execute(
			"INSERT INTO runsafe_inventories_regions (`worldName`,`regionName`) VALUES (?,?)",
			worldName, regionName
		);
	}

	/**
	 * Removes a specific inventory region.
	 * @param worldName The world the region is in.
	 * @param regionName The region to remove.
 	 */
	public void removeInventoryRegion(String worldName, String regionName)
	{
		database.execute(
			"DELETE FROM runsafe_inventories_regions WHERE `worldName`=? AND `regionName`=?",
			worldName, regionName
		);
	}

	public HashMap<String, List<String>> getInventoryRegions()
	{
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();

		ISet result = database.query("SELECT `worldName`, `regionName` FROM runsafe_inventories_regions");
		for (IRow row : result)
		{
			String worldName = row.String("worldName");
			if (!map.containsKey(worldName))
				map.put(worldName, new ArrayList<String>(1));

			map.get(worldName).add(row.String("regionName"));
		}

		return map;
	}
}
