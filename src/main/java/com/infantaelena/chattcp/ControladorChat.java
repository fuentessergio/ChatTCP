package com.infantaelena.chattcp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ControladorChat {
    private String nickname;
    private Socket socket;

    private Chat chat;
    private BufferedReader reader;
    private PrintWriter writer;
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;


    public void initialize() {
        // Configurar la acción del botón de enviar
        sendButton.setOnAction(event -> sendMessage());
    }
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        System.out.println("chat: " + chat);
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {

            System.out.println("Enviando mensaje al servidor: " + message);
            System.out.println("Nickname actual: " + nickname);

            writer.println(message);
            messageField.clear();
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Puedes realizar acciones adicionales si es necesario
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Iniciar hilo para recibir mensajes del servidor
        new Thread(this::receiveMessages).start();
    }

    private void receiveMessages() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Nickname actual: " + nickname);
                chatArea.appendText(nickname + ": " + message + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        // Lógica adicional si es necesario
    }
}