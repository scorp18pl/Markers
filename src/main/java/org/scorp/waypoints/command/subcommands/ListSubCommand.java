package org.scorp.waypoints.command.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorp.waypoints.InternalErrorException;
import org.scorp.waypoints.Utils;
import org.scorp.waypoints.Waypoint.WaypointManager;
import org.scorp.waypoints.command.CommandUtils;
import org.scorp.waypoints.command.PlayerOnlyCommandException;

import java.util.List;
import java.util.ArrayList;

public class ListSubCommand implements SubCommand
{
  @Override
  public String getName()
  {
    return "list";
  }

  @Override
  public String getUsageMessage()
  {
    return "/waypoint list";
  }

  @Override
  public List<Integer> getPossibleArgCounts()
  {
    return List.of(0);
  }

  @Override
  public void onCommand(CommandSender sender, String[] args) throws
      PlayerOnlyCommandException
  {
    if (!(sender instanceof Player player))
    {
      throw new PlayerOnlyCommandException();
    }

    ArrayList<String> userWaypointNames =
        Utils.waypointListToStringlist(
            WaypointManager.getUserWaypoints(player.getName()));

    ArrayList<String> publicWaypointNames =
        Utils.waypointListToStringlist(WaypointManager.getPublicWaypoints());

    String message =
        ChatColor.BOLD + "" + ChatColor.YELLOW + "Your waypoints: " +
            ChatColor.RESET + String.join(", ", userWaypointNames) +
            ChatColor.BOLD + "" + ChatColor.YELLOW + "\nPublic waypoints: " +
            ChatColor.RESET + String.join(", ", publicWaypointNames) + ".";

    player.sendMessage(message);
  }

  @Override
  public java.util.List<String> onTabComplete(CommandSender sender,
                                              int argIndex)
  {
    throw new InternalErrorException();
  }
}
