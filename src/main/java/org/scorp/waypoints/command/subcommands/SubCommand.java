package org.scorp.waypoints.command.subcommands;

import org.bukkit.command.CommandSender;
import org.scorp.waypoints.InternalErrorException;
import org.scorp.waypoints.Waypoint.WaypointNameExistsException;
import org.scorp.waypoints.Waypoint.WaypointNotFoundException;
import org.scorp.waypoints.command.InvalidCommandException;
import org.scorp.waypoints.command.PlayerOnlyCommandException;

import java.util.List;

public interface SubCommand
{
  String getName();

  String getUsageMessage();

  List<Integer> getPossibleArgCounts();

  void onCommand(CommandSender sender, String[] args) throws
      InvalidCommandException, WaypointNameExistsException,
      WaypointNotFoundException;

  List<String> onTabComplete(CommandSender sender, int argIndex) throws
      InternalErrorException, PlayerOnlyCommandException;
}
