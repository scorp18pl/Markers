package org.scorp.waypoints;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WaypointCommand implements CommandExecutor, TabExecutor
{
  @Override
  public boolean onCommand(@NotNull CommandSender sender,
                           @NotNull Command command, @NotNull String label,
                           @NotNull String[] args)
  {
    if (!(sender instanceof Player player))
    {
      sender.sendMessage("You must be a player to use this command.");
      return true;
    }

    if (args.length == 0)
    {
      return false;
    }

    final String playerName = player.getName();
    try
    {
      if (validateSubcommandCall("list", 0, args))
      {
        ArrayList<String> userWaypointNames = Utils.WaypointListToStringlist(
            WaypointManager.getUserWaypoints(playerName));

        ArrayList<String> publicWaypointNames = Utils.WaypointListToStringlist(
            WaypointManager.getPublicWaypoints());

        String message =
            ChatColor.BOLD + "" + ChatColor.YELLOW + "Your waypoints: " +
                ChatColor.RESET + String.join(", ", userWaypointNames) +
                ChatColor.BOLD + "" + ChatColor.YELLOW +
                "\nPublic waypoints: " + ChatColor.RESET +
                String.join(", ", publicWaypointNames) + ".";

        player.sendMessage(message);
      } else if (validateSubcommandCall("share", 0, args))
      {
        String waypointName = "current_location";
        Waypoint waypoint =
            new Waypoint(player.getLocation(), playerName, waypointName);
        Bukkit.broadcastMessage(
            ChatColor.YELLOW + playerName + "'s " + ChatColor.RESET +
                waypoint.toMcString());
      } else if (validateSubcommandCall("add", 1, args) ||
          (validateSubcommandCall("add", 2, args) &&
              (args[2].equals("private") || args[2].equals("public"))))
      {
        Boolean isPublic = args.length == 2 || args[2].equals("public");
        WaypointManager.addWaypoint(
            new Waypoint(player.getLocation(), playerName, args[1], isPublic));

        player.sendMessage(ChatColor.GREEN + "Successfully added a " +
            (isPublic ? "public" : "private") + " waypoint.");

      } else if (validateSubcommandCall("remove", 1, args))
      {
        WaypointManager.removeWaypoint(playerName, args[1]);
      } else if (validateSubcommandCall("coords", 1, args))
      {
        player.sendMessage(
            WaypointManager.getVisibleWaypoint(playerName, args[1])
                .toMcString());
      } else if (validateSubcommandCall("rename", 2, args))
      {
        WaypointManager.renameWaypoint(playerName, args[1], args[2]);

        player.sendMessage(ChatColor.GREEN + "Successfully renamed a " +
            (WaypointManager.getWaypoint(playerName, args[2]).isPublic ?
                "public" : "private") + " waypoint from " + args[1] + " to " +
            args[2]);
      } else if (validateSubcommandCall("set_type", 2, args) &&
          (args[2].equals("private") || args[2].equals("public")))
      {
        WaypointManager.setWaypointIsPublic(playerName, args[1],
            args[2].equals("public"));

        player.sendMessage(ChatColor.GREEN + "Successfully changed type of a " +
            (WaypointManager.getWaypoint(playerName, args[2]).isPublic ?
                "public" : "private") + " waypoint from " + args[1] + " to " +
            args[2]);
      } else
      {
        return false;
      }
      return true;

    } catch (WaypointNotFoundException e)
    {
      player.sendMessage(
          ChatColor.RED + "No waypoint with name " + args[1] + ".");
      return true;
    } catch (WaypointNameExistsException e)
    {
      player.sendMessage(
          ChatColor.RED + "Waypoint with name " + args[1] + " already exists.");
      return true;
    }
  }

  private static boolean validateSubcommandCall(String subcommandName,
                                                int argCount, String[] args)
  {
    if ((args.length == argCount + 1) && args[0].equals(subcommandName))
    {
      for (int i = 1; i < args.length; ++i)
      {
        if (!isNameValid(args[i]))
        {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                              @NotNull Command command,
                                              @NotNull String label,
                                              @NotNull String[] args)
  {
    if (args.length == 1)
    {
      return Arrays.asList("add", "rename", "remove", "list", "coords",
          "set_type", "share");
    }

    if (args.length == 2)
    {
      if (args[0].equals("rename") || args[0].equals("remove"))
      {
        return Utils.WaypointListToStringlist(
            WaypointManager.getUserWaypoints(sender.getName()));
      } else if (args[0].equals("coords"))
      {
        return Utils.WaypointListToStringlist(
            WaypointManager.getVisibleWaypoints(sender.getName()));
      } else if (args[0].equals("add"))
      {
        return List.of("waypointName");
      }
    }

    if (args.length == 3)
    {
      if (args[0].equals("rename"))
      {
        return List.of("newWaypointName");
      }
    }

    if ((args.length > 0) && (args[0].equals("add") && args.length == 3) ||
        (args[0].equals("set_type") && args.length == 3))
    {
      return Arrays.asList("private", "public");
    }

    return new ArrayList<>();
  }

  private static boolean isNameValid(String name)
  {
    final Pattern pattern =
        Pattern.compile("^[a-zA-z]+[a-zA-z0-9\\p{L}]{2,16}$");
    Matcher matcher = pattern.matcher(name);
    return matcher.matches();
  }
}
