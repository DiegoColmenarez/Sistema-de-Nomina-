package domain.vo;

import domain.exceptions.InvalidHorasTrabajadasException;

public record HoursWorked(short hours) {

    private static final int LIMITE_HORAS_NORMALES = 40;

    public HoursWorked {
        notNegatives(hours);
    }

    private void notNegatives(short hours){
        if (hours < 0) {
            throw InvalidHorasTrabajadasException.negativeValue();
        }
    }
}
