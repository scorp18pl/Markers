package org.scorp.waypoints.Waypoint;

import org.scorp.waypoints.command.InvalidCommandException;

public class WaypointNameExistsException extends Exception
{
  public WaypointNameExistsException(String waypointName)
  {
    super("Waypoint with name " + waypointName + " already exists.");
  }
}
