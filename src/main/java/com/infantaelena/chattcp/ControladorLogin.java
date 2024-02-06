package com.infantaelena.chattcp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.Socket;

public class ControladorLogin {
    @FXML
    private TextField nicknameField;

    @FXML
    private Button startButton;
    private Chat chatServidor;
    public ControladorLogin() {
    }

    public ControladorLogin(TextField nicknameField, Button startButton, Chat chatServidor) {
        setNicknameField(nicknameField);
        this.startButton = startButton;
        setChatServidor(chatServidor);
    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) throws Exception {
        if(nicknameField.getText() != null && !nicknameField.getText().isEmpty()){
            String nickname = nicknameField.getText();

            chatServidor.iniciarServidor(8000, nickname);

            Stage currentStage = (Stage) startButton.getScene().getWindow();
            currentStage.close();
            chatServidor.mostrarVistaChat(new Stage(), nickname, new Socket("localhost", 8000));

        }
    }


    public TextField getNicknameField() {
        return nicknameField;
    }

    public void setNicknameField(TextField nicknameField) {
        if (nicknameField != null){
            this.nicknameField = nicknameField;
        } else throw new RuntimeException("No hay nickname");
    }

    public Button getStartButton() {
        return startButton;
    }

    public void setStartButton(Button startButton) {
        this.startButton = startButton;
    }
    public Chat getChatServidor() {
        return chatServidor;
    }

    public void setChatServidor(Chat chatServidor) {
        if(chatServidor != null){
            this.chatServidor = chatServidor;
        } else throw new RuntimeException("ERROR");

    }

    public void initialize() {
        System.out.println("ControladorLogin initialized.");
    }
}
