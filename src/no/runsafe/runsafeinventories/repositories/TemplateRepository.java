package no.runsafe.runsafeinventories.repositories;

import no.runsafe.framework.database.IDatabase;
import no.runsafe.framework.database.Repository;
import no.runsafe.framework.server.inventory.RunsafeInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		this.database.Execute(
			"INSERT INTO `runsafe_inventories_templates` (universeName, inventory) VALUES(?,?) " +
				"ON DUPLICATE KEY UPDATE inventory = ?",
				universe, inventoryString, inventoryString
		);
	}

	public void setToTemplate(String universe, RunsafeInventory playerInventory)
	{
		Map<String, Object> data = this.database.QueryRow(
			"SELECT inventory FROM runsafe_inventories_templates WHERE universeName = ?", universe
		);

		if (data != null)
			playerInventory.unserialize((String) data.get("inventory"));
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

	private IDatabase database;
}
