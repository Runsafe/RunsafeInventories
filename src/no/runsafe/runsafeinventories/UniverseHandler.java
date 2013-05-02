package no.runsafe.runsafeinventories;

import no.runsafe.framework.server.RunsafeWorld;

public class UniverseHandler
{
	public String getUniverse(String worldName)
	{
		// TODO: Implement this.
		return null;
	}

	public String getUniverse(RunsafeWorld world)
	{
		return this.getUniverse(world.getName());
	}
}
