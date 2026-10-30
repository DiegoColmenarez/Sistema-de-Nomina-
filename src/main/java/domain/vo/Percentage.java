package domain.vo;

import domain.exceptions.InvalidPercentageException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Percentage(BigDecimal value) implements Comparable<Percentage> {

    public Percentage {
        notNull(value);
        notNegative(value);
        exceedsMaximun(value);
        value = value.setScale(3, RoundingMode.HALF_UP);
    }

    public Percentage(double value) {
        this(BigDecimal.valueOf(value));
    }

    public Percentage(String value) {
        this(new BigDecimal(value));
    }

    private BigDecimal getDecimalMultiplier() {
        return this.value.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
    }

    public Money applyTo(Money base) {
        return base.multiply(getDecimalMultiplier());
    }

    @Override
    public int compareTo(Percentage other) {
        return this.value.compareTo(other.value());
    }

    @Override
    public String toString() {
        return value.toString() + "%";
    }

    private void  notNull(BigDecimal value){
        if (value == null) {
            throw InvalidPercentageException.nullValue();
        }
    }

    private void notNegative(BigDecimal value){
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw InvalidPercentageException.negativeValue();
        }
    }

    private void exceedsMaximun(BigDecimal value){
        if (value.compareTo(new BigDecimal("100")) > 0) {
            throw InvalidPercentageException.exceedsMaximum();
        }
    }
}
