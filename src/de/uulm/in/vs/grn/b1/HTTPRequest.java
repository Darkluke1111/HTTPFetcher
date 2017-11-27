package de.uulm.in.vs.grn.b1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPRequest implements AutoCloseable {
  private static final int httpPort = 80;
  private static final String httpVersion = "HTTP/1.1";

  private final URL url;
  private final Socket socket;


  public HTTPRequest(String address) throws InvalidURLException, MalformedURLException {
    url = new URL("http://" + address);

    try {
      socket = new Socket(url.getHost(), httpPort);
    } catch (IOException e) {
      throw new InvalidURLException();
    }
  }

  public HTTPResponse sendRequest() throws IOException {

    try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
      bw.write(buildHttpRequest());
      bw.flush();

      String[] lines = br.lines().limit(20).toArray(String[]::new);

      return new HTTPResponse(lines);
    }
  }

  private String buildHttpRequest() {
    String request =
        "GET " + url.getPath() + " " + httpVersion + "\r\n"
            + "Host: " + url.getHost() + "\r\n"
            + "\r\n"
            + "\r\n";

    return request;
  }

  @Override
  public void close() throws IOException {

    socket.close();
  }
}
