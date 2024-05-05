package org.scorp.waypoints.command;

public class PlayerOnlyCommandException extends InvalidCommandException
{
  public PlayerOnlyCommandException()
  {
    super("You must be a player to use this command.");
  }
}
