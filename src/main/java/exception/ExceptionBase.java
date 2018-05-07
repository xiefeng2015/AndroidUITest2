package exception;

public class ExceptionBase extends Exception {
    public ExceptionBase(String message) {
        super(
                "\n************************************************************************************************\n" +
                        message +
                        "\n************************************************************************************************"
        );
    }

    public ExceptionBase(String message, Throwable cause) {
        super(message, cause);
    }
}


