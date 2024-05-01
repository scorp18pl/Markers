package org.scorp.waypoints;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class Waypoints extends JavaPlugin
{
  private FileConfiguration config;

  @Override
  public void onEnable()
  {
    if (!getDataFolder().exists())
    {
      getDataFolder().mkdir();
    }

    saveDefaultConfig();
    config = getConfig();

    WaypointManager.initializeDataFolder(getDataFolder());
    WaypointManager.initializeSocketAddress(config.getString("settings.host"),
        config.getInt("settings.port"));

    getCommand("waypoint").setExecutor(new WaypointCommand());
    Bukkit.getScheduler()
        .scheduleSyncRepeatingTask(this, WaypointManager::onTimeElapsed, 0, config.getInt("settings.interval"));
  }

  @Override
  public void onDisable()
  {
  }
}
