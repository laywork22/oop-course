package Eccezioni;

public class AutorimessaPienaException extends BoxException {
    public void AutorimessaVuotaException() {} ;
    public AutorimessaPienaException(String message) {
        super(message);
    }
}
