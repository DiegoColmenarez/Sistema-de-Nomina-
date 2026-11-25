package domain.exceptions;

public class InvalidHoursWorkedException extends DomainException {

    private InvalidHoursWorkedException(String message) {
        super(message);
    }

    public static final String NULL_VALUE_MSG = "Las horas trabajadas no pueden ser nulas.";
    public static final String NEGATIVE_VALUE_MSG = "Las horas trabajadas no pueden ser negativas.";

    public static InvalidHoursWorkedException negativeValue() {
        return new InvalidHoursWorkedException(NEGATIVE_VALUE_MSG);
    }

    public static InvalidHoursWorkedException nullValue(){
        return new InvalidHoursWorkedException(NULL_VALUE_MSG);
    }
}
