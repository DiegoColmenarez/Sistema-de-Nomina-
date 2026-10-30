package domain.vo;

import domain.exceptions.InvalidMoneyException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) implements Comparable<Money> {
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money {
        notNull(amount);
        notNegative(amount);
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    private void notNull(BigDecimal amount){
        if (amount == null) {
            throw InvalidMoneyException.nullAmount();
        }
    }

    private void notNegative(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw InvalidMoneyException.negativeAmount();
        }
    }

    @Override
    public int compareTo(Money other) {
        return this.amount.compareTo(other.amount());
    }
}
