package org.scorp.waypoints.command;

public class InvalidArgumentException extends InvalidCommandException
{
  public InvalidArgumentException(String argument)
  {
    super("Provided argument was invalid: \"" + argument + "\".");
  }
}
