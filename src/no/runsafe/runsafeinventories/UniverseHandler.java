package no.runsafe.runsafeinventories;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.server.RunsafeWorld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniverseHandler implements IConfigurationChanged
{
	public String getUniverseName(String worldName)
	{
		if (this.universes.containsKey(worldName))
			return this.universes.get(worldName);

		return worldName;
	}

	public String getUniverseName(RunsafeWorld world)
	{
		return this.getUniverseName(world.getName());
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		Map<String, List<String>> section = configuration.getConfigSectionsAsList("universes");

		for (String name : section.keySet())
		{
			List<String> worlds = section.get(name);
			for (String worldName : worlds)
				this.universes.put(worldName, name);
		}
	}

	private HashMap<String, String> universes = new HashMap<String, String>();
}
