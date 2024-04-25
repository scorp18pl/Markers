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

    public static Waypoint getWaypoint(String playerName, String waypointName)
    {
        for (Waypoint waypoint : waypoints)
        {
            if (waypoint.ownerName.equals(playerName) &&
                    waypoint.waypointName.equals(waypointName))
            {
                return waypoint;
            }
        }
        return null;
    }

    public static ArrayList<String> getUserWaypointNames(String userName)
    {
        return waypoints.stream()
                .filter(waypoint -> waypoint.ownerName.equals(userName))
                .map(waypoint -> waypoint.waypointName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean addWaypoint(Waypoint waypoint)
    {
        if (waypoint == null)
        {
            return false;
        }

        waypoints.add(waypoint);
        writeWaypoints();
        return true;
    }

    public static boolean renameWaypoint(String owner, String oldName,
                                         String newName)
    {
        Waypoint waypoint = getWaypoint(owner, oldName);
        if (waypoint == null)
        {
            return false;
        }
        waypoint.waypointName = newName;
        writeWaypoints();
        return true;
    }

    public static boolean removeWaypoint(String owner, String name)
    {
        Waypoint waypoint = getWaypoint(owner, name);
        if (waypoint == null || !waypoints.remove(waypoint))
        {
            return false;
        }

        writeWaypoints();
        return true;
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
