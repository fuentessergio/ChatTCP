package com.infantaelena.chattcp;

import com.infantaelena.chattcp.excepciones.ServerException;

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

    public void start() throws ServerException {
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
            throw new ServerException("El servidor no se ha podido iniciar correctamente. " + ex.getMessage());

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

    public static void main(String[] args) throws ServerException {
        final int PORT = 8000;
        Servidor server = new Servidor(PORT);
        server.start();
    }
}
