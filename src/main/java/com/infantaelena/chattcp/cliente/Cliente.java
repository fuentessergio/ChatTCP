package com.infantaelena.chattcp.cliente;

import com.infantaelena.chattcp.excepciones.MensajeException;
import com.infantaelena.chattcp.excepciones.NicknameException;
import com.infantaelena.chattcp.excepciones.ProcesamientoMensajeException;
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

    //Para manejar los mensajes que entran al servidor desde la interfaz gráfica (Chat)
    private Consumer<String> mensajeRecibido;

    public Cliente(String nickname) throws NicknameException {
        setNickname(nickname);
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) throws NicknameException {
        if(nickname != null){
            this.nickname = nickname;
        } else {
            throw new NicknameException("El nickname no puede ser nulo. Introduzca uno");
        }
    }

    public Consumer<String> getMensajeRecibido() {
        return mensajeRecibido;
    }

    public void setMensajeRecibido(Consumer<String> mensajeRecibido) throws ProcesamientoMensajeException {
        if(mensajeRecibido == null){
            throw new ProcesamientoMensajeException("El consumer de mensajes no puede ser null");
        }
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
            System.out.println("Servidor no encontrado: " + HOSTNAME + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida al conectar con el servidor: " + ex.getMessage());
        }
    }

    public void enviarMensaje(String mensaje) throws MensajeException {
        if (writer != null) {
            try{
                String mensajeOk = nickname + ": " + mensaje;
                writer.println(mensajeOk);
                writer.flush();
            } catch (Exception e){
                throw new MensajeException("Fallo en el flujo de escritura. " + e.getMessage());
            }
        }
    }
}
