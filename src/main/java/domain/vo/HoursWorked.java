package domain.vo;

import domain.exceptions.InvalidHorasTrabajadasException;

public record HoursWorked(int hours) {

    private static final int LIMITE_HORAS_NORMALES = 40;

    public HoursWorked {
        notNegatives((short) hours);
    }

    private void notNegatives(short hours){
        if (hours < 0) {
            throw InvalidHorasTrabajadasException.negativeValue();
        }
    }

    public int getHoursNormal() {
        return Math.min(hours, LIMITE_HORAS_NORMALES);
    }

    public int getOverTime() {
        return Math.max(0, hours - LIMITE_HORAS_NORMALES);
    }

    public boolean isOverTime() {
        return hours > LIMITE_HORAS_NORMALES;
    }

    public HoursWorked sumOvertime(HoursWorked hoursExtra) {
        return new HoursWorked(this.hours + hoursExtra.hours());
    }

    @Override
    public String toString() {
        return hours + " h";
    }
}
