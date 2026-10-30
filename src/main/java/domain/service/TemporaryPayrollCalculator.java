package domain.service;

import domain.vo.Money;
import java.util.Objects;

public final class TemporaryPayrollCalculator implements PayrollCalculator {

    private final Money monthlySalary;

    public TemporaryPayrollCalculator(Money monthlySalary) {
        this.monthlySalary = Objects.requireNonNull(monthlySalary, "El salario mensual no puede ser nulo.");
    }

    @Override
    public Money calculateGrossSalary() {
        return monthlySalary;
    }

    @Override
    public Money calculateBonuses() {
        return Money.ZERO;
    }

    @Override
    public Money calculateBenefits() {
        return Money.ZERO;
    }
}