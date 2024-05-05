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

public class RenameSubCommand implements SubCommand
{
  @Override
  public String getName()
  {
    return "rename";
  }

  @Override
  public String getUsageMessage()
  {
    return "/waypoint rename <oldName> <newName>\n" +
        " * <oldName> - (Required) " +
        CommandUtils.getAlreadyExistRequirementString() + "\n" +
        " * <newName> - (Optional) " +
        CommandUtils.getNameRequirementsString();
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
    String oldName = args[0];
    String newName = args[1];

    try
    {
      WaypointManager.renameWaypoint(playerName, oldName, newName);
    } catch (WaypointNotFoundException e)
    {
      throw new WaypointNameExistsException(oldName);
    } catch (WaypointNameExistsException e)
    {
      throw new WaypointNameExistsException(
          newName);
    }

    String visibility = null;
    try
    {
      visibility = WaypointManager.getWaypoint(playerName, newName).isPublic() ?
          "public" : "private";
    } catch (WaypointNotFoundException e)
    {
      throw new WaypointNameExistsException(newName);
    }

    player.sendMessage(Utils.getSuccessString(
        "Successfully renamed a " + visibility + " waypoint from " + oldName +
            " to " + newName));
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
      case 1 -> List.of("newName");
      default -> throw new InternalErrorException();
    };
  }
}
