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

    public Money(double amount) {
        this(BigDecimal.valueOf(amount));
    }

    public Money(String amount) {
        this(new BigDecimal(amount));
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount()));
    }

    public Money subtract(Money other) {
        BigDecimal result = this.amount.subtract(other.amount());
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw InvalidMoneyException.negativeSubtraction();
        }
        return new Money(result);
    }

    public Money multiply(double multiplier) {
        if (multiplier < 0) {
            throw InvalidMoneyException.negativeMultiplier();
        }
        return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)));
    }

    public boolean isGreaterThan(Money other) {
        return this.compareTo(other) > 0;
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

    @Override
    public String toString() {
        return "$" + amount.toString();
    }
}
