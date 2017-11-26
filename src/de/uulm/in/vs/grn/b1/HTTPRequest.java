package de.uulm.in.vs.grn.b1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPRequest implements AutoCloseable {
  private static final int httpPort = 80;
  private static final String httpVersion = "HTTP/1.1";
  private static final Pattern urlRegexp = Pattern.compile("^(https?://)?([\\w\\d.]+)(/[\\w\\d]+)*$");
  private String host;
  private String path;
  private Socket socket;


  public HTTPRequest(String url) throws InvalidURLException {
    Matcher matcher = urlRegexp.matcher(url);

    if (!matcher.matches()) {
      throw new InvalidURLException("The Url: " + url + " is not valid!");
    } else {
      host = matcher.group(2);
      path = matcher.group(3);
    }
    try {
      socket = new Socket(host, httpPort);
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
        "GET " + path + " " + httpVersion + "\r\n"
            + "Host: " + host + "\r\n"
            + "\r\n"
            + "\r\n";

    return request;
  }

  @Override
  public void close() throws IOException {

    socket.close();
  }
}
