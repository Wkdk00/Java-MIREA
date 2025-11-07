package p6;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Base64;

public class Main {
    private static final int PORT = 8080;
    private static final List<String> notes = new ArrayList<>();
    private static final String AUTH_CREDENTIALS = "user:1234";
    private static final String AUTH_HEADER_VALUE = "Basic " +
            Base64.getEncoder().encodeToString(AUTH_CREDENTIALS.getBytes());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Note HTTP Server запущен на порту " + PORT);
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    clientSocket.setSoTimeout(5000);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    OutputStream outputStream = clientSocket.getOutputStream();

                    String inputLine;
                    StringBuilder request = new StringBuilder();
                    String authHeader = null;
                    while ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
                        request.append(inputLine).append("\n");
                        if (inputLine.toLowerCase().startsWith("authorization:")) {
                            authHeader = inputLine.substring(14).trim();
                        }
                    }

                    if (!AUTH_HEADER_VALUE.equals(authHeader)) {
                        sendResponse(outputStream,
                                "HTTP/1.1 401 Unauthorized\r\n" +
                                        "WWW-Authenticate: Basic realm=\"Access to notes\"\r\n" +
                                        "Connection: close\r\n\r\n");
                        continue;
                    }

                    if (request.length() == 0) continue;

                    String[] requestLines = request.toString().split("\n");
                    String firstLine = requestLines[0].trim();
                    if (firstLine.isEmpty()) {
                        sendResponse(outputStream, "HTTP/1.1 400 Bad Request\r\n\r\n");
                        continue;
                    }

                    String[] parts = firstLine.split(" ");
                    if (parts.length < 2) {
                        sendResponse(outputStream, "HTTP/1.1 400 Bad Request\r\n\r\n");
                        continue;
                    }

                    String method = parts[0];
                    String path = parts[1];
                    String response;

                    if (method.equals("GET") && path.equals("/notes")) {
                        response = "HTTP/1.1 200 OK\r\nConnection: close\r\nContent-Type: text/html\r\n\r\n" +
                                "<html><body><h1>Notes</h1><ul>" +
                                notes.stream().map(note -> "<li>" + note + "</li>")
                                        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append) +
                                "</ul></body></html>";
                        sendResponse(outputStream, response);
                    } else if (method.equals("POST") && path.equals("/add")) {
                        StringBuilder body = new StringBuilder();
                        while (in.ready()) body.append((char) in.read());
                        String note = body.toString().trim();
                        if (!note.isEmpty()) {
                            notes.add(note);
                            response = "HTTP/1.1 200 OK\r\nConnection: close\r\nContent-Type: text/html\r\n\r\n" +
                                    "<html><body><h1>Note added</h1></body></html>";
                        } else {
                            response = "HTTP/1.1 400 Bad Request\r\nConnection: close\r\nContent-Type: text/html\r\n\r\n" +
                                    "<html><body><h1>Empty note</h1></body></html>";
                        }
                        sendResponse(outputStream, response);
                    } else if (method.equals("POST") && path.equals("/remove")) {
                        if (!notes.isEmpty()) {
                            notes.remove(notes.size() - 1);
                            response = "HTTP/1.1 200 OK\r\nConnection: close\r\nContent-Type: text/html\r\n\r\n" +
                                    "<html><body><h1>Last note removed</h1></body></html>";
                        } else {
                            response = "HTTP/1.1 400 Bad Request\r\nConnection: close\r\nContent-Type: text/html\r\n\r\n" +
                                    "<html><body><h1>No notes to remove</h1></body></html>";
                        }
                        sendResponse(outputStream, response);
                    } else {
                        response = "HTTP/1.1 404 Not Found\r\nConnection: close\r\nContent-Type: text/html\r\n\r\n" +
                                "<html><body><h1>404 Not Found</h1></body></html>";
                        sendResponse(outputStream, response);
                    }
                } catch (IOException ignored) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendResponse(OutputStream out, String response) throws IOException {
        out.write(response.getBytes("UTF-8"));
        out.flush();
    }
}
