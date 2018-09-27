package org.elsys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketsClient {

    private static final String SOCKET_HOST = "localhost";
    private static final int SOCKET_PORT = 7777;

    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;

        try {
            echoSocket = new Socket(SOCKET_HOST, SOCKET_PORT);

            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("Server response: " + in.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (echoSocket != null && !echoSocket.isClosed()) {
                echoSocket.close();
            }
        }
    }
}
