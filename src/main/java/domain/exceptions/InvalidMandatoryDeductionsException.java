package domain.exceptions;

public class InvalidMandatoryDeductionsException extends DomainException{

    public InvalidMandatoryDeductionsException(String message) {
        super(message);
    }

    public static final String NULL_PENSION_MSG = "El porcentaje de Seguro Social y Pensión no puede ser nulo.";
    public static final String NULL_ARL_MSG = "El porcentaje de ARL no puede ser nulo.";

    public static InvalidMandatoryDeductionsException nullPension() {
        return new InvalidMandatoryDeductionsException(NULL_PENSION_MSG);
    }

    public static InvalidMandatoryDeductionsException nullArl() {
        return new InvalidMandatoryDeductionsException(NULL_ARL_MSG);
    }
}