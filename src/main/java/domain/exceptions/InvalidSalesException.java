package domain.exceptions;

public class InvalidSalesException extends DomainException {

    private InvalidSalesException(String message) {
        super(message);
    }

    public static final String NULL_VALUE_MSG = "El monto de las ventas no puede ser nulo.";
    public static final String NEGATIVE_VALUE_MSG = "Las ventas de un empleado por comisión no pueden ser menores a $0.";

    public static InvalidSalesException nullValue() {
        return new InvalidSalesException(NULL_VALUE_MSG);
    }

    public static InvalidSalesException negativeValue() {
        return new InvalidSalesException(NEGATIVE_VALUE_MSG);
    }
}