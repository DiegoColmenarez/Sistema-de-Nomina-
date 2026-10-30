package domain.vo;

import domain.exceptions.InvalidSalesException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Sales(BigDecimal amount) {

    public Sales {
        notNull(amount);
        notNegative(amount);
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public Sales(double amount) {
        this(BigDecimal.valueOf(amount));
    }

    public Sales(String amount) {
        this(new BigDecimal(amount));
    }

    private void notNull(BigDecimal amount){
        if (amount == null) {
            throw InvalidSalesException.nullValue();
        }
    }

    private void notNegative(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw InvalidSalesException.negativeValue();
        }
    }
}
