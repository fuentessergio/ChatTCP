package com.infantaelena.chattcp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Chat{
    private static Chat instance;
    private Servidor servidor;

    private List<ClientHandler> clients = new ArrayList<>();
    public Chat() {
        this.servidor = new Servidor();
    }
    public static Chat getInstance() {
        if (instance == null) {
            instance = new Chat();
        }
        return instance;
    }

    public Servidor getServidor() {
        return instance.servidor;
    }

    public boolean isServidorIniciado(){
        return servidor.isServidorIniciado();

    }

    public List<ClientHandler> getClients() {
        return clients;
    }

    public void setClients(List<ClientHandler> clients) {
        this.clients = clients;
    }

    public void lanzarMensaje(String message, ClientHandler clientHandler) {
        for (ClientHandler client : clients) {
            if (client != clientHandler) {
                client.sendMessage(message);
            }
        }
    }
    public void borrarCliente(ClientHandler client) {
        clients.remove(client);
        System.out.println("Cliente desconectado: " + client.getNickname());
    }

    public void mostrarVistaChat(Stage primaryStage, String nickname, Socket socket) {
        System.out.println("mostrarVistaChat ejecutado.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);

            // Configurar el controlador de chat
            ControladorChat chatController = loader.getController();
            chatController.setChat(chatController.getChat());


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
