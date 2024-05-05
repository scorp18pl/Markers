package org.scorp.waypoints.command.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorp.waypoints.InternalErrorException;
import org.scorp.waypoints.Utils;
import org.scorp.waypoints.Waypoint.Waypoint;
import org.scorp.waypoints.Waypoint.WaypointManager;
import org.scorp.waypoints.Waypoint.WaypointNameExistsException;
import org.scorp.waypoints.command.*;

import java.util.List;

public class AddSubCommand implements SubCommand
{
  @Override
  public String getName()
  {
    return "add";
  }

  @Override
  public String getUsageMessage()
  {
    return "/waypoint add <waypointName> <visibility>\n" +
        " * <waypointName> - (Required) " +
        CommandUtils.getNameRequirementsString() + "\n" +
        " * <visibility> - (Optional) " +
        CommandUtils.getVisibilityRequirementsString();
  }

  @Override
  public List<Integer> getPossibleArgCounts()
  {
    return List.of(1, 2);
  }

  @Override
  public void onCommand(CommandSender sender, String[] args) throws
      InvalidCommandException, WaypointNameExistsException
  {
    if (!(sender instanceof Player player))
    {
      throw new PlayerOnlyCommandException();
    }

    if (args.length == 2 && !(args[1].equals(CommandUtils.Visibility.PUBLIC) ||
        args[1].equals(CommandUtils.Visibility.PRIVATE)))
    {
      throw new InvalidArgumentException(args[1]);
    }

    String playerName = player.getName();

    String waypointName = args[0];
    Boolean isPublic =
        args.length == 1 || args[1].equals(CommandUtils.Visibility.PUBLIC);
    String visibility = isPublic ? CommandUtils.Visibility.PUBLIC :
        CommandUtils.Visibility.PRIVATE;

    try
    {
      WaypointManager.addWaypoint(
          new Waypoint(player.getLocation(), playerName, waypointName,
              isPublic));
    } catch (WaypointNameExistsException e)
    {
      throw new WaypointNameExistsException(waypointName);
    }

    player.sendMessage(Utils.getSuccessString(
        "Successfully added a " + visibility + " waypoint with name " +
            waypointName + "."));
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, int argIndex) throws
      InternalErrorException
  {
    return switch (argIndex)
    {
      case 0 -> List.of("waypointName");
      case 1 -> List.of(CommandUtils.Visibility.PRIVATE,
          CommandUtils.Visibility.PUBLIC);
      default -> throw new InternalErrorException();
    };
  }
}
