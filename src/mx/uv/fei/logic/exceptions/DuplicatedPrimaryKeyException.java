package mx.uv.fei.logic.exceptions;

public class DuplicatedPrimaryKeyException extends Exception{
    public DuplicatedPrimaryKeyException(String message){
        super(message);
    }
}
