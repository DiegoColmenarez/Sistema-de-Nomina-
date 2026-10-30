package domain.vo;

import domain.exceptions.InvalidSalesException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public record Sales(BigDecimal amount) {

    private static final BigDecimal EXTRA_BONUS_THRESHOLD = new BigDecimal("20000000");

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

    public boolean isEligibleForExtraBonus() {
        return this.amount.compareTo(EXTRA_BONUS_THRESHOLD) > 0;
    }

    public Money asMoney() {
        return new Money(amount);
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

    @Override
    public String toString() {
        return "$" + amount.toString();
    }
}