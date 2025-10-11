package Eccezioni;

public class TargaNonValidaException extends Exception {
    public  TargaNonValidaException(){};

    public TargaNonValidaException(String message) {
        super(message);
    }
}
