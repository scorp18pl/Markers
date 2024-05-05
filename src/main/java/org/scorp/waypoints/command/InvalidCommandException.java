package org.scorp.waypoints.command;

public class InvalidCommandException extends Exception
{
  public InvalidCommandException()
  {
    super("Provided command was not valid");
  }

  public InvalidCommandException(String message)
  {
    super(message);
  }
}
