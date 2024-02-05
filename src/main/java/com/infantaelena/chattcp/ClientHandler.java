package com.infantaelena.chattcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private ChatServidor chatServer;
    private String nickname;

    public ClientHandler(Socket socket, ChatServidor server) {
        this.clientSocket = socket;
        this.chatServer = server;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            nickname = reader.readLine();
            System.out.println("Nuevo usuario conectado: " + nickname);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println(nickname + ": " + message);
                chatServer.lanzarMensaje(message, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            chatServer.borrarCliente(this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
