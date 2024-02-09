package com.infantaelena.chattcp;

import com.infantaelena.chattcp.excepciones.NicknameException;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.function.Consumer;

public class Cliente implements Serializable {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8000;
    private String nickname;

    private Socket socket;
    private PrintWriter writer;

    private Consumer<String> mensajeRecibido;

    public Cliente(String nickname) throws NicknameException {
        setNickname(nickname);
    }

    public void setMensajeRecibido(Consumer<String> mensajeRecibido) {
        this.mensajeRecibido = mensajeRecibido;
    }

    public void execute() {
        try {
            socket = new Socket(HOSTNAME,PORT);

            System.out.println("Conectado al servidor soy " + getNickname());

            writer = new PrintWriter(socket.getOutputStream(), true);

            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message;
                    while ((message = reader.readLine()) != null) {
                        if (mensajeRecibido != null) {
                            String finalMessage = message;
                            Platform.runLater(() -> {
                                mensajeRecibido.accept(finalMessage);
                            });
                        } else {
                            System.out.println("no he recibido nada");
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error leyendo del servidor: " + e.getMessage());
                }
            }).start();
        } catch (UnknownHostException ex) {
            System.out.println("Servidor no encontrado: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }

    public void setNickname(String nickname) throws NicknameException {
        if(nickname != null){
            this.nickname = nickname;
        } else {
            throw new NicknameException("El nickname no puede ser nulo. Introduzca uno");
        }

    }
    public void enviarMensaje(String mensaje) {
        if (writer != null) {
            String mensajeOk = nickname + ": " + mensaje;
            writer.println(mensajeOk);
            writer.flush();
        }
    }

    public String getNickname() {
        return this.nickname;
    }

}
