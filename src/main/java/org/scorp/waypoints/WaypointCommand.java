package org.scorp.waypoints;

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
        if (validateSubcommandCall("list", 0, args))
        {
            ArrayList<String> waypointNames =
                    WaypointManager.getUserWaypointNames(playerName);
            player.sendMessage(String.join(", ", waypointNames));
            return true;
        } else if (validateSubcommandCall("add", 1, args))
        {
            Waypoint waypoint =
                    new Waypoint(player.getLocation(), playerName, args[1]);
            return WaypointManager.addWaypoint(waypoint);
        } else if (validateSubcommandCall("remove", 1, args))
        {
            return WaypointManager.removeWaypoint(playerName, args[1]);
        } else if (validateSubcommandCall("coords", 1, args))
        {
            Waypoint waypoint =
                    WaypointManager.getWaypoint(playerName, args[1]);
            if (waypoint == null)
            {
                player.sendMessage("No waypoint with name " + args[1] + ".");
                return true;
            } else
            {
                player.sendMessage(waypoint.toString());
            }

            return true;
        } else if (validateSubcommandCall("rename", 2, args))
        {
            return WaypointManager.renameWaypoint(playerName, args[1], args[2]);
        } return false;
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
            return Arrays.asList("add", "rename", "remove", "list", "coords");
        }

        if (args.length == 2)
        {
            if (args[0].equals("rename") || args[0].equals("remove") ||
                    args[0].equals("coords"))
            {
                return WaypointManager.getUserWaypointNames(sender.getName());
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

        return new ArrayList<>();
    }

    private static boolean isNameValid(String name)
    {
        final Pattern pattern = Pattern.compile("^[a-zA-z]+[a-zA-z0-9]*$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
