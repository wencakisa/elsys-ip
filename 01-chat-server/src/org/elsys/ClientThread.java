package org.elsys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends ChatServer implements Runnable {

    private static final String EXIT_WORD = "exit";

    private Socket clientSocket;

    private BufferedReader reader;

    private PrintWriter writer;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    private PrintWriter getWriter() {
        return writer;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String userInput;
            while ((userInput = reader.readLine()) != null) {
                if (userInput.equals(EXIT_WORD)) {
                    clientSocket.close();
                }

                for (ClientThread clientThread : clients) {
                    clientThread.getWriter().write(userInput);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
