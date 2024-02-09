package com.infantaelena.chattcp;

import com.infantaelena.chattcp.excepciones.ClienteNotFoundException;
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
        sendButton.setOnAction(event -> {
            try {
                sendMessage();
            } catch (ClienteNotFoundException e) {
                System.err.println("Cliente no encontrado " + e.getMessage());
            }
        });

        // Configura el campo de texto para enviar el mensaje al presionar Enter
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendMessage(); // Llama al método sendMessage
                } catch (ClienteNotFoundException e) {
                    System.err.println("El cliente no se ha podido encontrar " + e.getMessage());
                }
                event.consume(); // Evita la propagación del evento (opcional)
            }
        });
    }

    public void iniciarCliente(Cliente cliente) throws ClienteNotFoundException {
        if (cliente != null) {
            this.cliente = cliente;
            System.out.println("Conectado al servidor soy " + cliente.getNickname());
            this.cliente.setMensajeRecibido(message -> {
                Platform.runLater(() -> {
                    chatArea.appendText(message + "\n");
                });
            });
        } else {
            throw new ClienteNotFoundException("El cliente no se ha podido inicializar.");
        }
    }


    @FXML
    private void sendMessage() throws ClienteNotFoundException {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            if (cliente != null) {
                cliente.enviarMensaje(message);
                messageField.clear();
            } else {
                throw new ClienteNotFoundException("El cliente no es nulo o no se ha inicializado correctamente.");
            }
        }
    }
}