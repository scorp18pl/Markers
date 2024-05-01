package org.scorp.waypoints;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;


public class Utils
{
  public static ArrayList<String> WaypointListToStringlist(
      ArrayList<Waypoint> waypoints)
  {
    return waypoints.stream().map(waypoint -> waypoint.waypointName)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public static void sendTCPMessage(String serverAddress, int port,
                                    String message)
  {
    try (Socket socket = new Socket(serverAddress, port))
    {
      OutputStream out = socket.getOutputStream();
      out.write(message.getBytes(StandardCharsets.UTF_8));
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public static String getDateString()
  {
    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    return sdf.format(date);
  }
}
