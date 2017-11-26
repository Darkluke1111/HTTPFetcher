package de.uulm.in.vs.grn.b1;

import java.io.IOException;

public class URLFetcher {
  public static void main(String[] args) {
    try {
      HTTPRequest request = new HTTPRequest("stackoverflow.com/questions");
      HTTPResponse response = request.sendRequest();
      request.close();

    } catch (InvalidURLException | IOException e) {
      e.printStackTrace();
    }
  }



}

