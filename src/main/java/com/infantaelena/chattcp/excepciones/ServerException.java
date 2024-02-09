package com.infantaelena.chattcp.excepciones;

public class ServerException extends Exception{
    public ServerException(String mensaje){
        super(mensaje);
    }
    public ServerException(String mensaje,Throwable causa){
        super(mensaje, causa);
    }
}
