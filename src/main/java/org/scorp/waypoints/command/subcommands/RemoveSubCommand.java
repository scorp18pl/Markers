package org.scorp.waypoints.command.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorp.waypoints.InternalErrorException;
import org.scorp.waypoints.Utils;
import org.scorp.waypoints.Waypoint.WaypointManager;
import org.scorp.waypoints.Waypoint.WaypointNameExistsException;
import org.scorp.waypoints.Waypoint.WaypointNotFoundException;
import org.scorp.waypoints.command.CommandUtils;
import org.scorp.waypoints.command.InvalidCommandException;
import org.scorp.waypoints.command.PlayerOnlyCommandException;

import java.util.List;

public class RemoveSubCommand implements SubCommand
{
  @Override
  public String getName()
  {
    return "remove";
  }

  @Override
  public String getUsageMessage()
  {
    return "/waypoint remove <waypointName>\n" +
        " * <waypointName> - (Required) " +
        CommandUtils.getAlreadyExistRequirementString();
  }

  @Override
  public List<Integer> getPossibleArgCounts()
  {
    return List.of(1);
  }

  @Override
  public void onCommand(CommandSender sender, String[] args) throws
      InvalidCommandException, WaypointNotFoundException
  {
    if (!(sender instanceof Player player))
    {
      throw new PlayerOnlyCommandException();
    }

    String waypointName = args[0];

    try
    {
      WaypointManager.removeWaypoint(player.getName(), waypointName);
    } catch (WaypointNotFoundException e)
    {
      throw new WaypointNotFoundException(waypointName);
    }
    player.sendMessage(Utils.getSuccessString(
        "Successfully removed a waypoint with name " + waypointName + "."));
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, int argIndex) throws
      PlayerOnlyCommandException
  {
    if (!(sender instanceof Player player))
    {
      throw new PlayerOnlyCommandException();
    }

    return switch (argIndex)
    {
      case 0 -> Utils.waypointListToStringlist(
          WaypointManager.getUserWaypoints(player.getName()));
      default -> throw new InternalErrorException();
    };
  }
}
