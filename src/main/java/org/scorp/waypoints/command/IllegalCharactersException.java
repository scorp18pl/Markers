package org.scorp.waypoints.command;

public class IllegalCharactersException extends InvalidCommandException
{
  public IllegalCharactersException(String argument)
  {
    super("Provided argument \"" + argument +
        "\" contains illegal characters or its size is invalid.");
  }
}
