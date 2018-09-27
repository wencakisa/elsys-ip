package org.elsys;

import com.sun.security.ntlm.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private static final int SOCKET_PORT = 7777;

    protected static List<ClientThread> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(SOCKET_PORT);

            while (true) {
                Socket clientServer = serverSocket.accept();
                System.out.println("Client connected from: " + clientServer.getInetAddress());

                ClientThread clientThread = new ClientThread(clientServer);
                clients.add(clientThread);
                new Thread(clientThread).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
    }
}
