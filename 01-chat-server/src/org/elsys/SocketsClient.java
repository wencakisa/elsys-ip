package org.elsys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketsClient {

    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;

        try {
            echoSocket = new Socket(Config.SOCKET_HOST, Config.SOCKET_PORT);

            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            new Thread(new ServerListener(in, out, stdIn)).start();

            String serverInput;
            while ((serverInput = in.readLine()) != null) {
                System.out.println(serverInput);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (echoSocket != null && !echoSocket.isClosed()) {
                echoSocket.close();
            }
        }
    }

    private static final class ServerListener implements Runnable {

        private BufferedReader reader;

        private PrintWriter writer;

        private BufferedReader stdIn;

        public ServerListener(BufferedReader reader, PrintWriter writer, BufferedReader stdIn) {
            this.reader = reader;
            this.writer = writer;
            this.stdIn = stdIn;
        }

        @Override
        public void run() {
            String userInput;

            try {
                while ((userInput = stdIn.readLine()) != null) {
                    this.writer.println(userInput);
                    System.out.println(this.reader.readLine());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
