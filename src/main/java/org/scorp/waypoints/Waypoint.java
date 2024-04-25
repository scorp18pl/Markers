package org.scorp.waypoints;

import org.bukkit.Location;

public class Waypoint
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
}
