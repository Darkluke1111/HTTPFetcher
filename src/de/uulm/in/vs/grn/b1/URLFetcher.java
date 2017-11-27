package de.uulm.in.vs.grn.b1;

import java.io.IOException;

public class URLFetcher {
  public static void main(String[] args) {
    if(args.length != 1) {
      System.out.println("Das erste Parameter sollte die URL sein!");
    } else {
      try {
        String url = args[0];
        HTTPRequest request = new HTTPRequest(url);
        HTTPResponse response = request.sendRequest();
        System.out.println(response.getContent());
        request.close();

      } catch (InvalidURLException | IOException e) {
        e.printStackTrace();
      }
    }
  }



}

