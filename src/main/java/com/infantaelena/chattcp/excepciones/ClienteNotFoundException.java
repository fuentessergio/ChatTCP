package com.infantaelena.chattcp.excepciones;

public class ClienteNotFoundException extends Exception{
    public ClienteNotFoundException(String mensaje){
        super(mensaje);
    }
    public ClienteNotFoundException(String mensaje, Throwable cause){
        super(mensaje, cause);
    }
}
