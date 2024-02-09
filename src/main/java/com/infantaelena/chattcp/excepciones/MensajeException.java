package com.infantaelena.chattcp.excepciones;

public class MensajeException extends Exception{
    public MensajeException(String mensaje){
        super(mensaje);
    }
    public MensajeException(String mensaje, Throwable cause){
        super(mensaje, cause);
    }
}
