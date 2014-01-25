package no.runsafe.runsafeinventories.repositories;

import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;

public class TemplateRepository extends Repository
{
	@Override
	public String getTableName()
	{
		return "runsafe_inventories_templates";
	}

	public void insertTemplate(String universe, RunsafeInventory inventory)
	{
		String inventoryString = inventory.serialize();
		database.execute(
			"INSERT INTO `runsafe_inventories_templates` (universeName, inventory) VALUES(?,?) " +
				"ON DUPLICATE KEY UPDATE inventory = ?",
			universe, inventoryString, inventoryString
		);
	}

	public void setToTemplate(String universe, RunsafeInventory playerInventory)
	{
		String serialized = database.queryString(
			"SELECT inventory FROM runsafe_inventories_templates WHERE universeName = ?", universe
		);
		if (serialized != null)
			playerInventory.unserialize(serialized);
	}

	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate update = new SchemaUpdate();

		update.addQueries(
			"CREATE TABLE `runsafe_inventories_templates` (" +
				"`universeName` varchar(50) NOT NULL, " +
				"`inventory` longtext, " +
				"PRIMARY KEY (`universeName`)" +
				")"
		);

		return update;
	}
}
