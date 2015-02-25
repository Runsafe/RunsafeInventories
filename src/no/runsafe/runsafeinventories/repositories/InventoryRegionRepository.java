package no.runsafe.runsafeinventories.repositories;

import no.runsafe.framework.api.database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryRegionRepository extends Repository
{
	@Override
	public String getTableName()
	{
		return "runsafe_inventories_regions";
	}

	@Override
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

		return update;
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
