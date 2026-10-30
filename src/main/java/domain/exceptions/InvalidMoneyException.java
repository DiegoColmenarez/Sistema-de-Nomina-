package domain.exceptions;

public class InvalidMoneyException  extends  DomainException{
    public InvalidMoneyException(String message) {
        super(message);
    }

    public static final String NULL_AMOUNT_MSG = "El monto de dinero no puede ser nulo.";
    public static final String NEGATIVE_AMOUNT_MSG = "El monto de dinero no puede ser negativo.";
    public static final String NEGATIVE_SUBTRACTION_MSG = "El resultado de la resta no puede resultar en un valor negativo.";
    public static final String NEGATIVE_MULTIPLIER_MSG = "El multiplicador no puede ser negativo.";

    public static InvalidMoneyException nullAmount() {
        return new InvalidMoneyException(NULL_AMOUNT_MSG);
    }
}
