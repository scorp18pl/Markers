package org.scorp.waypoints;

public class InternalErrorException extends IllegalStateException
{
  public InternalErrorException()
  {
    this("An internal error has occurred.");
  }

  public InternalErrorException(String message)
  {
    super(message);
  }
}
