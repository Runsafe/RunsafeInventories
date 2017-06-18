package no.runsafe.runsafeinventories;

import com.google.common.collect.Lists;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IUniverseManager;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.hook.IUniverseMapper;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.server.IWorldManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniverseHandler implements IConfigurationChanged, IUniverseMapper
{
	public UniverseHandler(IConsole output, IWorldManager worldManager)
	{
		this.output = output;
		this.worldManager = worldManager;
	}

	public boolean universeExists(String universeName)
	{
		return this.universes.containsValue(universeName);
	}

	public boolean worldExists(String worldName)
	{
		return worldManager.getWorld(worldName) != null;
	}

	@Override
	public List<String> GetUniverses()
	{
		return Lists.newArrayList(worlds.keySet());
	}

	@Override
	public List<String> GetWorlds(String universe)
	{
		if (!worlds.containsKey(universe))
			return null;
		return worlds.get(universe);
	}

	@Override
	public String GetUniverse(String world)
	{
		if (universes.containsKey(world))
			return universes.get(world);
		return null;
	}

	@Override
	public void setManager(IUniverseManager manager)
	{
		this.manager = manager;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		Map<String, List<String>> section = configuration.getConfigSectionsAsList("universes");

		for (String name : section.keySet())
		{
			List<String> worlds = section.get(name);
			this.output.logInformation("Universe %s found with %s worlds.", name, worlds.size());
			for (String worldName : worlds)
				this.universes.put(worldName, name);

			if (!this.worlds.containsKey(name))
				this.worlds.put(name, new ArrayList<>());
			this.worlds.get(name).addAll(worlds);
		}
		manager.flush();
	}

	private final HashMap<String, String> universes = new HashMap<>();
	private final HashMap<String, List<String>> worlds = new HashMap<>();
	private final IConsole output;
	private final IWorldManager worldManager;
	private IUniverseManager manager;
}
