package com.infantaelena.chattcp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class ControladorLogin {
    @FXML
    private TextField nicknameField;

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private void handleStartButtonAction() {
        String nickname = nicknameField.getText().trim();
        if (nickname != null && !nickname.isEmpty()  ) {
            Cliente cliente = new Cliente(nickname);
            cliente.execute();

            mostrarVistaChat(stage, cliente);
        }
    }
    public void mostrarVistaChat(Stage stage, Cliente cliente) {
        System.out.println("mostrarVistaChat ejecutado.");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);

            // Configurar el controlador de chat
            ControladorChat chatController = loader.getController();
            chatController.iniciarCliente(cliente);
            stage.setTitle("Chat");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

