package com.infantaelena.chattcp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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

public class ChatCliente extends Application {
    private String clientName;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private TextArea chatArea;
    private TextField messageField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        chatArea = new TextArea();
        chatArea.setEditable(false);
        messageField = new TextField();
        Button sendButton = new Button("Enviar");

        root.getChildren().addAll(chatArea, messageField, sendButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Chat Cliente");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        primaryStage.show();

        startClient("localhost", 8080);  // Debes especificar la dirección y puerto del servidor
        new Thread(new ReceiveMessages()).start();

        sendButton.setOnAction(event -> sendMessage(messageField.getText()));
        messageField.setOnAction(event -> sendMessage(messageField.getText()));
    }

    public void startClient(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            clientName = "Usuario"; // Puedes personalizar el nombre del usuario aquí
            writer.println(clientName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        if (!message.isEmpty()) {
            writer.println(message);
            messageField.clear();
        }
    }

    private class ReceiveMessages implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    chatArea.appendText(message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}