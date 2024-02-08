package com.infantaelena.chattcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Servidor {
    private int port;
    private Set<ClienteHilo> clientHandlers = new HashSet<>(); // para que no haya duplicados

    public Servidor(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado en el puerto: " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nuevo usuario conectado");

                ClienteHilo nuevoUsuario = new ClienteHilo(socket, this);
                clientHandlers.add(nuevoUsuario);
                new Thread(nuevoUsuario).start();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void difundirMensaje(String message) {
        for (ClienteHilo usuario : clientHandlers) {
            usuario.enviarMensaje(message);
        }
    }

    public void borrarUsuario(ClienteHilo usuario) {
        clientHandlers.remove(usuario);
        System.out.println("El usuario se ha desconectado del chat");
    }

    public static void main(String[] args) {
        final int PORT = 8000;
        Servidor server = new Servidor(PORT);
        server.start();
    }
}
