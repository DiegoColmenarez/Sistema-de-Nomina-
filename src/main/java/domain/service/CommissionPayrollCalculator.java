package domain.service;

import domain.vo.Money;
import domain.vo.Percentage;
import domain.vo.Sales;

import java.util.Objects;

public final class CommissionPayrollCalculator implements PayrollCalculator {

    private static final Percentage EXTRA_BONUS_RATE = new Percentage(3);
    private static final Money FOOD_ALLOWANCE = new Money("1000000");
    private final Money baseSalary;
    private final Sales sales;
    private final Percentage commissionRate;

    public CommissionPayrollCalculator(Money baseSalary, Sales sales, Percentage commissionRate) {
        this.baseSalary = Objects.requireNonNull(baseSalary, "El salario base no puede ser nulo.");
        this.sales = Objects.requireNonNull(sales, "Las ventas no pueden ser nulas.");
        this.commissionRate = Objects.requireNonNull(commissionRate, "El porcentaje de comisión no puede ser nulo.");
    }

    @Override
    public Money calculateGrossSalary() {
        return baseSalary.add(commissionRate.applyTo(sales.asMoney()));
    }

    @Override
    public Money calculateBonuses() {
        return sales.isEligibleForExtraBonus()
                ? EXTRA_BONUS_RATE.applyTo(sales.asMoney())
                : Money.ZERO;
    }

    @Override
    public Money calculateBenefits() {
        return FOOD_ALLOWANCE;
    }
}