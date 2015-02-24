package no.runsafe.runsafeinventories.repositories;

import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;

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
}
