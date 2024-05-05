package org.scorp.waypoints.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandUtils
{
  public static class Visibility
  {
    public static String PUBLIC = "public";
    public static String PRIVATE = "private";
  }

  public static String getNameRequirementsString()
  {
    return "Name containing at least 2 and at most" +
        " 16 polish and english characters, the \"_\" character and digits.";
  }

  public static String getAlreadyExistRequirementString()
  {
    return "Name of an already existing waypoint.";
  }

  public static String getVisibilityRequirementsString()
  {
    return "Either \"public\" or \"private\".";
  }

  public static boolean isNameValid(String name)
  {
    final Pattern pattern =
        Pattern.compile("^[a-zA-z]+[a-zA-z0-9\\p{L}]{2,16}$");
    Matcher matcher = pattern.matcher(name);
    return matcher.matches();
  }
}
