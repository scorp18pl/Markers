package org.scorp.waypoints;

import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class Waypoint implements
    Comparable<Waypoint>
{
  public String ownerName, waypointName, worldName;
  int x, y, z;
  boolean isPublic;

  public Waypoint(Location location, String ownerName, String waypointName)
  {
    this(location, ownerName, waypointName, false);
  }

  public Waypoint(Location location, String ownerName, String waypointName,
                  Boolean isPublic)
  {
    this.ownerName = ownerName;
    this.waypointName = waypointName;
    this.worldName = location.getWorld().getName();

    x = location.getBlockX();
    y = location.getBlockY();
    z = location.getBlockZ();

    this.isPublic = isPublic;
  }

  @Override
  public String toString()
  {
    return waypointName + " ( " + worldName + " )" + ": " + "(" + x + ", " +
        y + ", " + z + ")";
  }

  public String toMcString()
  {
    return ChatColor.YELLOW + waypointName + ChatColor.WHITE + " ( " +
        ChatColor.YELLOW + worldName + ChatColor.WHITE + " )" + ": " + "(" +
        ChatColor.RED + x + ChatColor.WHITE +
        ", " +
        ChatColor.GREEN + y + ChatColor.WHITE + ", " + ChatColor.AQUA + z +
        ChatColor.WHITE + ")";
  }

  @Override
  public int compareTo(@NotNull Waypoint o)
  {
    int compareOwner = this.ownerName.compareTo(o.ownerName);
    if (compareOwner != 0)
    {
      return compareOwner;
    }

    int compareWorld = this.worldName.compareTo(o.ownerName);
    if (compareWorld != 0)
    {
      return compareWorld;
    }

    return this.waypointName.compareTo(o.waypointName);
  }
}
