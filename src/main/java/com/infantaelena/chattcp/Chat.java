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

public class Chat extends Application {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();


    public void iniciarServidor(int port) {
        try {
            serverSocket = new ServerSocket(port);
            //log("Servidor de chat iniciado en el puerto " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                //log("Nuevo cliente conectado: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
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

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Iniciando servidor...");
        iniciarServidor(8000);
        mostrarVistaChat(stage,this);
    }
    private void mostrarVistaChat(Stage primaryStage, Chat chat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);

            // Configurar el controlador de chat
            ControladorChat chatController = loader.getController();
            Socket socket = new Socket("localhost", 8000);

            chatController.setSocket(socket);

            primaryStage.setTitle("Chat");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
