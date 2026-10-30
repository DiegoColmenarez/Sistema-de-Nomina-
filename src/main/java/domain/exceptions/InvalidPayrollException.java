package domain.exceptions;

public class InvalidPayrollException extends DomainException {
    public InvalidPayrollException(String message) {
        super(message);
    }

    public static final String NEGATIVE_NET_MSG = "Ningún empleado puede tener un salario neto negativo.";

    public static InvalidPayrollException negativeNet() {
        return new InvalidPayrollException(NEGATIVE_NET_MSG);
    }
}
