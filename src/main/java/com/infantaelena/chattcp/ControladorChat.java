package com.infantaelena.chattcp;

import com.infantaelena.chattcp.Cliente;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ControladorChat {

    private Cliente cliente;

    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;
    @FXML
    private Button sendButton;

    public void initialize() {
        sendButton.setOnAction(event -> sendMessage());

        // Configura el campo de texto para enviar el mensaje al presionar Enter
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendMessage(); // Llama al método sendMessage
                event.consume(); // Evita la propagación del evento (opcional)
            }
        });
    }

    public void iniciarCliente(Cliente cliente) {
        if (cliente != null) {
            this.cliente = cliente;
            System.out.println("Conectado al servidor soy " + cliente.getId());
            this.cliente.setMensajeRecibido(message -> {
                Platform.runLater(() -> {
                    chatArea.appendText(message + "\n");
                });
            });
        }
    }


    @FXML
    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            if (cliente != null) {
                cliente.enviarMensaje(message);
                messageField.clear();
            } else {
                System.out.println("cliente nulo");
            }
        }
    }
}