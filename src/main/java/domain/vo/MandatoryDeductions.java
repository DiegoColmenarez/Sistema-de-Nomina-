package domain.vo;

import domain.exceptions.InvalidMandatoryDeductionsException;

public record MandatoryDeductions(Percentage pension, Percentage arl) {

    // Seguro Social y Pensión equivalente al 4%.
    private static final Percentage STANDARD_PENSION_RATE = new Percentage("4");

    public MandatoryDeductions {
        notNull(pension, arl);
    }

    private void notNull(Percentage pension, Percentage arl){
        if (pension == null) {
            throw InvalidMandatoryDeductionsException.nullPension();
        }
        if (arl == null) {
            throw InvalidMandatoryDeductionsException.nullArl();
        }
    }

    public static MandatoryDeductions standard(Percentage arl) {
        return new MandatoryDeductions(STANDARD_PENSION_RATE, arl);
    }

    public Money calculateOn(Money base) {
        return pension.applyTo(base).add(arl.applyTo(base));
    }
}