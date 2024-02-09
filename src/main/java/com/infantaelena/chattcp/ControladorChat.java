package com.infantaelena.chattcp;

import com.infantaelena.chattcp.cliente.Cliente;
import com.infantaelena.chattcp.excepciones.ClienteNotFoundException;
import com.infantaelena.chattcp.excepciones.MensajeException;
import com.infantaelena.chattcp.excepciones.ProcesamientoMensajeException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ControladorChat {

    private Cliente cliente;

    @FXML
    private Label label;

    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;
    @FXML
    private Button sendButton;

    public ControladorChat(){

    }

    public void initialize() {
        sendButton.setOnAction(event -> {
            try {
                sendMessage();
            } catch (ClienteNotFoundException e) {
                System.err.println("Cliente no encontrado " + e.getMessage());
            } catch (MensajeException e) {
                System.err.println("Fallo en el flujo de escritura del programa. " + e.getMessage());
            }
        });

        // Configura el campo de texto para enviar el mensaje al presionar Enter
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendMessage(); // Llama al método sendMessage
                } catch (ClienteNotFoundException e) {
                    System.err.println("El cliente no se ha podido encontrar " + e.getMessage());
                } catch (MensajeException e) {
                    System.err.println("Fallo en el flujo de escritura del programa. " + e.getMessage());
                }
                event.consume(); // Evita la propagación del evento (opcional)
            }
        });
    }

    public void iniciarCliente(Cliente cliente) throws ClienteNotFoundException, ProcesamientoMensajeException {
        if (cliente != null) {
            this.cliente = cliente;
            this.cliente.setMensajeRecibido(message -> {
                Platform.runLater(() -> {
                    System.out.println("mensaje recibido");
                    chatArea.appendText(message + "\n");
                });
            });
        } else {
            throw new ClienteNotFoundException("El cliente no se ha podido inicializar.");
        }
    }


    @FXML
    private void sendMessage() throws ClienteNotFoundException, MensajeException {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            if (cliente != null) {
                System.out.println("mensaje enviado");
                cliente.enviarMensaje(message);
                messageField.clear();
            } else {
                throw new ClienteNotFoundException("El cliente es nulo o no se ha inicializado correctamente.");
            }
        }
    }
    public void setLabel(Cliente cliente){
        label.setText("Nickname: " + cliente.getNickname());
    }
}