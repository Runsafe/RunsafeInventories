package no.runsafe.runsafeinventories.repositories;

import no.runsafe.framework.api.database.IDatabase;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TemplateRepository extends Repository
{
	public TemplateRepository(IDatabase database)
	{
		this.database = database;
	}

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
	public HashMap<Integer, List<String>> getSchemaUpdateQueries()
	{
		HashMap<Integer, List<String>> versions = new HashMap<Integer, List<String>>();
		ArrayList<String> sql = new ArrayList<String>();
		sql.add(
			"CREATE TABLE `runsafe_inventories_templates` (" +
				"`universeName` varchar(50) NOT NULL, " +
				"`inventory` longtext, " +
				"PRIMARY KEY (`universeName`)" +
				")"
		);
		versions.put(1, sql);
		return versions;
	}

	private final IDatabase database;
}
