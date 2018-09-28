package org.elsys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private static List<PrintWriter> writers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(Config.SOCKET_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(Config.CLIENT_CONNECTED_PREFIX + clientSocket.getInetAddress());

                new Thread(new ClientThread(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }

            System.out.println(Config.SERVER_CLOSED_MESSAGE);
        }
    }


    private static final class ClientThread implements Runnable {

        private BufferedReader reader;

        private PrintWriter writer;

        public ClientThread(Socket clientSocket) throws IOException {
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
        }

        @Override
        public void run() {
            try {
                writers.add(this.writer);

                String userInput;
                while ((userInput = this.reader.readLine()) != null) {
                    if (userInput.equals(Config.EXIT_WORD)) {
                        break;
                    }

                    System.out.println(userInput);

                    for (PrintWriter printWriter : writers) {
                        printWriter.write(userInput);
                    }
                }

                System.out.println(Config.CLIENT_DISCONNECTED_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
