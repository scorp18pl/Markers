package org.scorp.waypoints;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WaypointManager
{
  static final String WaypointsFileName = "waypoints.json";
  static private File dataFolder;
  static private ArrayList<Waypoint> waypoints = new ArrayList<>();

  public static void initializeDataFolder(File dataFolder)
  {
    WaypointManager.dataFolder = dataFolder;
    readWaypoints();
  }

  public static Waypoint getWaypoint(String playerName,
                                     String waypointName) throws
      WaypointNotFoundException
  {
    for (Waypoint waypoint : waypoints)
    {
      if (waypoint.ownerName.equals(playerName) &&
          waypoint.waypointName.equals(waypointName))
      {
        return waypoint;
      }
    }

    throw new WaypointNotFoundException();
  }

  public static ArrayList<Waypoint> getUserWaypoints(String userName)
  {
    return waypoints.stream()
        .filter(waypoint -> waypoint.ownerName.equals(userName))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public static ArrayList<Waypoint> getPublicWaypoints()
  {
    return waypoints.stream()
        .filter(waypoint -> waypoint.isPublic)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public static void addWaypoint(Waypoint waypoint) throws
      WaypointNameExistsException
  {
    try
    {
      getWaypoint(waypoint.ownerName, waypoint.waypointName);
    } catch (WaypointNotFoundException e)
    {
      waypoints.add(waypoint);
      writeWaypoints();
      return;
    }
    throw new WaypointNameExistsException();
  }

  public static void renameWaypoint(String owner, String oldName,
                                    String newName) throws
      WaypointNotFoundException, WaypointNameExistsException
  {
    try
    {
      getWaypoint(owner, newName);
    } catch (WaypointNotFoundException e)
    {
      getWaypoint(owner, oldName).waypointName = newName;
      writeWaypoints();
      return;
    }
    throw new WaypointNameExistsException();
  }

  public static void setWaypointIsPublic(String owner, String waypointName,
                                         boolean isPublic) throws
      WaypointNotFoundException
  {
    getWaypoint(owner, waypointName).isPublic = isPublic;
    writeWaypoints();
  }

  public static void removeWaypoint(String owner, String name) throws
      WaypointNotFoundException
  {
    waypoints.remove(getWaypoint(owner, name));
    writeWaypoints();
  }

  private static void readWaypoints()
  {
    try
    {
      Gson gson = new Gson();
      FileReader reader =
          new FileReader(dataFolder + "/" + WaypointsFileName);
      waypoints = gson.fromJson(reader, new TypeToken<List<Waypoint>>()
      {
      }.getType());
      reader.close();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  private static void writeWaypoints()
  {
    try
    {
      Gson gson = new Gson();
      FileWriter writer =
          new FileWriter(dataFolder + "/" + WaypointsFileName);
      gson.toJson(waypoints, writer);
      writer.close();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
