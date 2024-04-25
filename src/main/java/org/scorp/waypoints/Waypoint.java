package org.scorp.waypoints;

import org.bukkit.Location;

public class Waypoint
{
    public String ownerName, waypointName, worldName;
    int x, y, z;

    public Waypoint(Location location, String ownerName, String waypointName)
    {
        this.ownerName = ownerName;
        this.waypointName = waypointName;
        this.worldName = location.getWorld().getName();

        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
    }

    @Override
    public String toString()
    {
        return waypointName + ": " + "(" + x + ", " + y + ", " + z + ")";
    }
}
