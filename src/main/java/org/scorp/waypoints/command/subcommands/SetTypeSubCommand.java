package org.scorp.waypoints.command.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorp.waypoints.InternalErrorException;
import org.scorp.waypoints.Utils;
import org.scorp.waypoints.Waypoint.WaypointManager;
import org.scorp.waypoints.Waypoint.WaypointNameExistsException;
import org.scorp.waypoints.Waypoint.WaypointNotFoundException;
import org.scorp.waypoints.command.CommandUtils;
import org.scorp.waypoints.command.InvalidArgumentException;
import org.scorp.waypoints.command.InvalidCommandException;
import org.scorp.waypoints.command.PlayerOnlyCommandException;

import java.util.List;

public class SetTypeSubCommand implements SubCommand
{
  @Override
  public String getName()
  {
    return "set_type";
  }

  @Override
  public String getUsageMessage()
  {
    return "/waypoint add <waypointName> <visibility>\n" +
        " * <waypointName> - (Required) " +
        CommandUtils.getAlreadyExistRequirementString() + "\n" +
        " * <visibility> - (Required) " +
        CommandUtils.getVisibilityRequirementsString();
  }

  @Override
  public List<Integer> getPossibleArgCounts()
  {
    return List.of(2);
  }

  @Override
  public void onCommand(CommandSender sender, String[] args) throws
      InvalidCommandException, WaypointNameExistsException
  {
    if (!(sender instanceof Player player))
    {
      throw new PlayerOnlyCommandException();
    }

    String playerName = player.getName();
    String waypointName = args[0];
    String visibility = args[1];

    if (!(visibility.equals(CommandUtils.Visibility.PRIVATE) ||
        visibility.equals(CommandUtils.Visibility.PUBLIC)))
    {
      throw new InvalidArgumentException(visibility);
    }

    boolean isPublic = visibility.equals(CommandUtils.Visibility.PUBLIC);

    try
    {
      WaypointManager.setWaypointIsPublic(playerName, waypointName, isPublic);
    } catch (WaypointNotFoundException e)
    {
      throw new WaypointNameExistsException(waypointName);
    }

    player.sendMessage(Utils.getSuccessString(
        "Successfully set the visibility of waypoint " + waypointName + " to " +
            visibility));
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
      case 1 -> List.of(CommandUtils.Visibility.PRIVATE,
          CommandUtils.Visibility.PUBLIC);
      default -> throw new InternalErrorException();
    };
  }
}
