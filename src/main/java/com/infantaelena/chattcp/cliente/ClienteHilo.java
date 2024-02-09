package com.infantaelena.chattcp.cliente;

import com.infantaelena.chattcp.Servidor;

import java.io.*;
import java.net.Socket;

import java.io.*;
import java.net.Socket;

public class ClienteHilo implements Runnable {
    private Socket socket;
    private Servidor server;
    private PrintWriter output;
    private BufferedReader input;

    public ClienteHilo(Socket socket, Servidor server) {
        this.socket = socket;
        this.server = server;
        try {
            // Inicializa PrintWriter para enviar mensajes al cliente
            output = new PrintWriter(socket.getOutputStream(), true);

            // Inicializa BufferedReader para leer mensajes del cliente
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error al crear los flujos de entrada/salida: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String mensaje;
            // Continuar leyendo mensajes mientras la conexión esté activa
            while ((mensaje = input.readLine()) != null) {
                if (mensaje.equalsIgnoreCase("*")) {
                    break; // se desconecta si pulsa *
                }
                server.difundirMensaje(mensaje);
            }
        } catch (IOException e) {
            System.out.println("Error en ClienteHilo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

    private void cerrarConexion() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar el socket: " + e.getMessage());
        }
        server.borrarUsuario(this);
    }

    public void enviarMensaje(String message) {
        // Envía el mensaje al cliente
        output.println(message);
    }
}



