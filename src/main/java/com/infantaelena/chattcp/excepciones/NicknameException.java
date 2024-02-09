package com.infantaelena.chattcp.excepciones;

public class NicknameException extends Exception{
    public NicknameException(String mensaje){
        super(mensaje);
    }
    public NicknameException(String mensaje, Throwable causa){
        super(mensaje, causa);
    }
}
