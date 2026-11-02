package domain.service;

import domain.vo.Money;
import domain.vo.Percentage;
import domain.vo.Sales;
import java.util.Objects;

public final class CommissionPayrollCalculator implements PayrollCalculator {

    private static final Percentage COMMISSION_RATE = new Percentage(3);
    private static final Money FOOD_ALLOWANCE = new Money("1000000");
    private final Money baseSalary;
    private final Sales sales;

    public CommissionPayrollCalculator(Money baseSalary, Sales sales) {
        this.baseSalary = Objects.requireNonNull(baseSalary, "El salario base no puede ser nulo.");
        this.sales = Objects.requireNonNull(sales, "Las ventas no pueden ser nulas.");
    }

    @Override
    public Money calculateGrossSalary() {
        return baseSalary.add(commission());
    }

    @Override
    public Money calculateBonuses() {
        return Money.ZERO;
    }

    @Override
    public Money calculateBenefits() {
        return FOOD_ALLOWANCE;
    }

    private Money commission() {
        return sales.isEligibleForCommission()
                ? COMMISSION_RATE.applyTo(sales.asMoney())
                : Money.ZERO;
    }
}