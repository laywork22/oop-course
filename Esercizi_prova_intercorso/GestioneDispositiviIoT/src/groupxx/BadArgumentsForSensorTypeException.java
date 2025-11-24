package groupxx;

public class BadArgumentsForSensorTypeException extends RuntimeException {
    public BadArgumentsForSensorTypeException() {}
    public BadArgumentsForSensorTypeException(String message) {
        super(message);
    }
}
