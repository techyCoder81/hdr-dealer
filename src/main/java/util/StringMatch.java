package util;

public class StringMatch {
  private StringMatch(){}

  public static boolean wildcard(String value, String pattern) {
    if (pattern == null) {
      return false;
    }

    // if its just a wildcard, return true
    if (pattern.contentEquals("*")) {
      return true;
    }

    // if the given value is empty, return false
    if (value == null || value.length() == 0) {
      return false;
    }

    // if there isnt a wildcard, simple contains check
    if (!pattern.contains("*")) {
      return value.contains(pattern);
    }

    String[] parts = pattern.split("\\*");
    int searchIndex = 0;
    
    for (String part : parts) {
      // find where this part next occurs
      int foundIndex = value.indexOf(part, searchIndex);

      // if not found, return false
      if (foundIndex == -1) {
        return false;
      }

      // set the search index for the next iteration
      searchIndex = foundIndex;
    
    }

    return true;
  }
}
