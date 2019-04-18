package backend.Exceptions;

public class JailException extends RuntimeException {

    public JailException (String message){
        super(message);
    }

    public JailException (String message, Object values) {
        super(String.format(message, values));
    }

    public JailException (Throwable cause, String message, Object values) {
        super(String.format(message, values), cause);
    }

}
