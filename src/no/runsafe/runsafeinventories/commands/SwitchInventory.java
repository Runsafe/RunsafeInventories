package no.runsafe.runsafeinventories.commands;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.argument.AnyPlayerRequired;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.SelfOrAnyPlayer;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.runsafeinventories.InventoryHistory;

public class SwitchInventory extends PlayerCommand
{
	public SwitchInventory(InventoryHistory history, IServer server)
	{
		super(
			"switch", "Moves a players inventory to the target.", "runsafe.inventories.switch",
			new AnyPlayerRequired("source"), new SelfOrAnyPlayer("target")
		);
		this.history = history;
		this.server = server;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		IPlayer source = server.getPlayer(parameters.get("source"));
		IPlayer target = server.getPlayer(parameters.get("target"));

		if (source == null)
			return "&cCould not find the source player";

		if (target == null)
			return "&cCould not find the target player";

		RunsafeInventory targetInventory = target.getInventory();
		RunsafeInventory sourceInventory = source.getInventory();

		this.history.save(target);
		this.history.save(source);

		targetInventory.clear();
		targetInventory.unserialize(sourceInventory.serialize());

		sourceInventory.clear();

		source.updateInventory();
		target.updateInventory();

		return String.format("Inventory of %s moved to %s.", source.getPrettyName(), target.getPrettyName());
	}

	private final InventoryHistory history;
	private final IServer server;
}
