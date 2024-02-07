package com.infantaelena.chattcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private ServerSocket serverSocket;
    private boolean servidorIniciado = false;

    public boolean isServidorIniciado() {
        return servidorIniciado;
    }

    public void iniciarServidor(int port, Chat chat) {
        if (!servidorIniciado) {
            try {
                serverSocket = new ServerSocket(port);
                servidorIniciado = true;

                new Thread(() -> {
                    while (servidorIniciado) {
                        try {
                            Socket clientSocket = serverSocket.accept();
                            System.out.println("Servidor iniciado correctamente");
                            ClientHandler clientHandler = new ClientHandler(clientSocket, chat);
                            chat.getClients().add(clientHandler);
                            new Thread(clientHandler).start();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cerrarServidor() {
        if (servidorIniciado) {
            servidorIniciado = false;

            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        // Crear una instancia de Servidor
        Servidor servidor = new Servidor();


        // Iniciar el servidor
        servidor.iniciarServidor(8000, new Chat());

    }
}
