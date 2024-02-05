package com.infantaelena.chattcp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ChatServidor extends Application {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        /*ChatServidor chatServer = new ChatServidor();
        chatServer.iniciarServidor(8080);*/
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Chat Server");
        stage.setScene(scene);
        stage.show();
        ControladorLogin loginController = loader.getController();
        iniciarServidor(8080, loginController);

    }


    public void iniciarServidor(int port, ControladorLogin login) {
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

    /*public void log(String message) {
        Platform.runLater(() -> logArea.appendText(message + "\n"));
    }*/
}

