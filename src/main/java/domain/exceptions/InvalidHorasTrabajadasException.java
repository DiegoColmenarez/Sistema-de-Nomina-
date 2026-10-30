package domain.exceptions;

public class InvalidHorasTrabajadasException extends DomainException {

    private InvalidHorasTrabajadasException(String message) {
        super(message);
    }

    public static final String NEGATIVE_VALUE_MSG = "Las horas trabajadas no pueden ser negativas.";

    public static InvalidHorasTrabajadasException negativeValue() {
        return new InvalidHorasTrabajadasException(NEGATIVE_VALUE_MSG);
    }
}
