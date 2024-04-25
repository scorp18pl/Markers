package org.scorp.waypoints;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Utils
{
    public static ArrayList<String> WaypointListToStringlist(
            ArrayList<Waypoint> waypoints)
    {
        return waypoints.stream().map(waypoint -> waypoint.waypointName)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
