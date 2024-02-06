package com.infantaelena.chattcp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Chat{
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();

    private boolean servidorAbierto = true;

    public void iniciarServidor(int port, String nickname) {
        try {
            serverSocket = new ServerSocket(port);
            //log("Servidor de chat iniciado en el puerto " + port);

            new Thread(() -> {
                while (servidorAbierto) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                        clients.add(clientHandler);
                        new Thread(clientHandler).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cerrarServidor() {
        try {
            servidorAbierto = false;

            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                // También puedes realizar otras acciones necesarias aquí
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lanzarMensaje(String message, ClientHandler clientHandler) {
        for (ClientHandler client : clients) {
            if (client != clientHandler) {
                client.sendMessage(clientHandler.getNickname() + ": " + message);
            }
        }
    }

    public void borrarCliente(ClientHandler client) {
        clients.remove(client);
        System.out.println("Cliente desconectado: " + client.getNickname());
    }

    public void mostrarVistaChat(Stage primaryStage, String nickname, Socket socket) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);

            // Configurar el controlador de chat
            ControladorChat chatController = loader.getController();


            chatController.setSocket(socket);
            chatController.setNickname(nickname);

            primaryStage.setTitle("Chat");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
