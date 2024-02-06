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

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
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
                chatArea.appendText(message + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void appendMessageToChatArea(String message) {
        // Este método permite que la actualización de la GUI se realice en el hilo de la interfaz de usuario
        chatArea.appendText(message + "\n");
    }
}