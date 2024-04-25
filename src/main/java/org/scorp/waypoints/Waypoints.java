package org.scorp.waypoints;

import org.bukkit.plugin.java.JavaPlugin;

public class Waypoints extends JavaPlugin
{
  @Override
  public void onEnable()
  {
    if (!getDataFolder().exists())
    {
      getDataFolder().mkdir();
    }

    WaypointManager.initializeDataFolder(getDataFolder());

    getCommand("waypoint").setExecutor(new WaypointCommand());
  }

  @Override
  public void onDisable()
  {
  }
}
