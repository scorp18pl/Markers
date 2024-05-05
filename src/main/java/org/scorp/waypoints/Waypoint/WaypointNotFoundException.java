package org.scorp.waypoints.Waypoint;

public class WaypointNotFoundException extends Exception
{
  public WaypointNotFoundException(String waypointName)
  {
    super("No waypoint with name " + waypointName + ".");
  }
}