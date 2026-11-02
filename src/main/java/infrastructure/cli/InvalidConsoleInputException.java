package infrastructure.cli;

public class InvalidConsoleInputException extends RuntimeException {

    private InvalidConsoleInputException(String message) {
        super(message);
    }

    static InvalidConsoleInputException missing(String label) {
        return new InvalidConsoleInputException("El dato '" + label + "' es obligatorio.");
    }

    static InvalidConsoleInputException notANumber(String label) {
        return new InvalidConsoleInputException("El dato '" + label + "' debe ser un valor numérico.");
    }
}