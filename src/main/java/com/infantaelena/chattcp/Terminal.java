package com.infantaelena.chattcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Terminal {
    private String clientName;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public static void main(String[] args) {
        Terminal chatClient = new Terminal();
        chatClient.startClient("localhost", 8080);
    }

    public void startClient(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Ingrese su nombre de usuario: ");
            clientName = new BufferedReader(new InputStreamReader(System.in)).readLine();
            writer.println(clientName);

            new Thread(new ReceiveMessages()).start();

            while (true) {
                String message = new BufferedReader(new InputStreamReader(System.in)).readLine();
                writer.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ReceiveMessages implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

