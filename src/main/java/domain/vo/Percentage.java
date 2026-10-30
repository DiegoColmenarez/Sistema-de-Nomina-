package domain.vo;

import domain.exceptions.InvalidPercentageException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Percentage(BigDecimal value) {

    public Percentage {
        notNull(value);
        notNegative(value);
        exceedsMaximun(value);
        value = value.setScale(2, RoundingMode.HALF_UP);
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
