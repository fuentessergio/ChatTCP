package com.infantaelena.chattcp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main2 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loginLoader.load();
            Scene loginScene = new Scene(root, 300, 200);

            ControladorLogin loginController = loginLoader.getController();
            loginController.setStage(stage);

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


