package com.infantaelena.chattcp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Crear una instancia única de Chat
            Chat chat = new Chat();

            // Cargar la vista de login
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loginLoader.load();

            // Obtener el controlador de login y establecer la instancia de Chat
            ControladorLogin loginController = loginLoader.getController();
            loginController.setChatServidor(chat);

            // Configurar la escena y mostrar la ventana de inicio de sesión
            Scene loginScene = new Scene(root, 300, 200);
            stage.setTitle("Inicio");
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
