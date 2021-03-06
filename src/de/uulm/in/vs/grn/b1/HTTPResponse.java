package de.uulm.in.vs.grn.b1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPResponse {
  private static final Pattern statusPattern = Pattern.compile("HTTP/\\d\\.\\d (\\d\\d\\d) (\\w| )*");
  private int statusCode;
  private String statusString;
  private Map<String, String> httpTags = new HashMap<>();
  private String content;

  public HTTPResponse(String[] lines) {
    System.out.println("hallo");

    int i = 0;
    Matcher m = statusPattern.matcher(lines[i]);
    if (m.matches()) {
      statusCode = Integer.parseInt(m.group(1));
      statusString = m.group(2);
    }
    i++;

    for (; i < lines.length && !lines[i].equals(""); i++) {
      String[] temp = lines[i].split(": ");
      String key = temp[0];
      String value = temp[1];
      httpTags.put(key, value);
      System.out.print(".");
    }
    i++;

    StringBuilder sb = new StringBuilder();
    for (; i < lines.length; i++) {
      sb.append(lines[i] + "\n");
      System.out.print("!");
    }
    content = sb.toString();
  }


  public String getContent() {
    return content;
  }

  public String getStatusString() {
    return statusString;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public Map<String, String> getHttpTags() {
    return httpTags;
  }
}
