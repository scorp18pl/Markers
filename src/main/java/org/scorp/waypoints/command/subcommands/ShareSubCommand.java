package org.scorp.waypoints.command.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scorp.waypoints.InternalErrorException;
import org.scorp.waypoints.Waypoint.Waypoint;
import org.scorp.waypoints.command.InvalidCommandException;
import org.scorp.waypoints.command.PlayerOnlyCommandException;

import java.util.List;

public class ShareSubCommand implements SubCommand
{
  @Override
  public String getName()
  {
    return "share";
  }

  @Override
  public String getUsageMessage()
  {
    return "/waypoint share";
  }

  @Override
  public List<Integer> getPossibleArgCounts()
  {
    return List.of(0);
  }

  @Override
  public void onCommand(CommandSender sender, String[] args) throws
      InvalidCommandException
  {
    if (!(sender instanceof Player player))
    {
      throw new PlayerOnlyCommandException();
    }

    String playerName = player.getName();

    String waypointName = "current_location";
    Waypoint waypoint =
        new Waypoint(player.getLocation(), playerName, waypointName);
    Bukkit.broadcastMessage(
        ChatColor.YELLOW + playerName + "'s " + ChatColor.RESET +
            waypoint.toMcString());
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, int argIndex)
  {
    throw new InternalErrorException();
  }
}
