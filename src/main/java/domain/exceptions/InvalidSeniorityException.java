package domain.exceptions;

public class InvalidSeniorityException extends DomainException {

    private InvalidSeniorityException(String message) {
        super(message);
    }

    public static final String NEGATIVE_VALUE_MSG = "La antigüedad del empleado no puede ser negativa.";

    public static InvalidSeniorityException negativeValue() {
        return new InvalidSeniorityException(NEGATIVE_VALUE_MSG);
    }
}