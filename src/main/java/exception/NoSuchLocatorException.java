package exception;

public class NoSuchLocatorException extends ExceptionBase {
    public NoSuchLocatorException(String reason) {
        super(reason);
    }

    public NoSuchLocatorException(String reason,Throwable cause){
        super(reason,cause);
    }
}
