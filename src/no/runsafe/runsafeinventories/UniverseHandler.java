package no.runsafe.runsafeinventories;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.RunsafeWorld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniverseHandler implements IConfigurationChanged
{
	public UniverseHandler(IOutput output)
	{
		this.output = output;
	}

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

	public boolean universeExists(String universeName)
	{
		return this.universes.containsValue(universeName);
	}

	public boolean worldExists(String worldName)
	{
		return RunsafeServer.Instance.getWorld(worldName) != null;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		Map<String, List<String>> section = configuration.getConfigSectionsAsList("universes");

		for (String name : section.keySet())
		{
			List<String> worlds = section.get(name);
			this.output.write(String.format("Universe %s found with %s worlds.", name, worlds.size()));
			for (String worldName : worlds)
				this.universes.put(worldName, name);
		}
	}

	private HashMap<String, String> universes = new HashMap<String, String>();
	private IOutput output;
}
