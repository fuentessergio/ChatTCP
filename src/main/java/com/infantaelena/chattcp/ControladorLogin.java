package com.infantaelena.chattcp;

import com.infantaelena.chattcp.cliente.Cliente;
import com.infantaelena.chattcp.ControladorChat;
import com.infantaelena.chattcp.excepciones.ClienteNotFoundException;
import com.infantaelena.chattcp.excepciones.NicknameException;
import com.infantaelena.chattcp.excepciones.ProcesamientoMensajeException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorLogin {
    @FXML
    private TextField nicknameField;

    @FXML
    private Button inicioButton;

    @FXML
    private Label mensajeAdvertenciaLabel;

    private Stage stage;

    public ControladorLogin(){

    }
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    private void handleStartButtonAction() throws NicknameException {
        String nickname = nicknameField.getText().trim().toUpperCase();
        if (nickname == null || nickname.isEmpty()) {
            mensajeAdvertenciaLabel.setVisible(true);
        } else {
            mensajeAdvertenciaLabel.setVisible(false);
            Cliente cliente = new Cliente(nickname);
            cliente.execute();

            try {
                mostrarVistaChat(stage, cliente);
            } catch (ClienteNotFoundException e) {
                System.err.println("El cliente no se ha podido encontrar " + e.getMessage());
            }
        }
    }
    public void mostrarVistaChat(Stage stage, Cliente cliente) throws ClienteNotFoundException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);

            ControladorChat chatController = loader.getController();

            try {
                chatController.iniciarCliente(cliente);
                chatController.setLabel(cliente);
            } catch (ProcesamientoMensajeException e) {
                System.err.println("Error al procesar el mensaje del servidor. " + e.getMessage());
            }
            stage.setTitle("Chat");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista del chat. " + e.getMessage());
        }
    }
}

