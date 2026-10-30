package domain.exceptions;

public class InvalidPercentageException extends DomainException{

    public InvalidPercentageException(String message) {
        super(message);
    }

    public static final String NULL_VALUE_MSG = "El valor del porcentaje no puede ser nulo.";
    public static final String NEGATIVE_VALUE_MSG = "El porcentaje no puede ser negativo.";
    public static final String EXCEEDS_MAXIMUM_MSG = "El porcentaje no puede ser mayor a 100.";

    public static InvalidPercentageException nullValue() {
        return new InvalidPercentageException(NULL_VALUE_MSG);
    }

    public static InvalidPercentageException negativeValue() {
        return new InvalidPercentageException(NEGATIVE_VALUE_MSG);
    }

    public static InvalidPercentageException exceedsMaximum() {
        return new InvalidPercentageException(EXCEEDS_MAXIMUM_MSG);
    }
}