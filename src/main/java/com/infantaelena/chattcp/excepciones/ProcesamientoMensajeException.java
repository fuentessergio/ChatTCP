package com.infantaelena.chattcp.excepciones;

public class ProcesamientoMensajeException extends Exception{
    public ProcesamientoMensajeException(String mensaje){
        super(mensaje);
    }
    public ProcesamientoMensajeException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
