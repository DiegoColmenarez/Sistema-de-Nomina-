package domain.service;

import domain.vo.Money;
import domain.vo.Percentage;
import domain.vo.Seniority;
import java.util.Objects;

public final class SalariedPayrollCalculator implements PayrollCalculator {

    private static final Percentage SENIORITY_BONUS_RATE = new Percentage(10);
    private static final Money FOOD_ALLOWANCE = new Money("1000000");
    private final Money monthlySalary;
    private final Seniority seniority;

    public SalariedPayrollCalculator(Money monthlySalary, Seniority seniority) {
        this.monthlySalary = Objects.requireNonNull(monthlySalary, "El salario mensual no puede ser nulo.");
        this.seniority = Objects.requireNonNull(seniority, "La antigüedad no puede ser nula.");
    }

    @Override
    public Money calculateGrossSalary() {
        return monthlySalary;
    }

    @Override
    public Money calculateBonuses() {
        return seniority.isEligibleForSeniorityBonus()
                ? SENIORITY_BONUS_RATE.applyTo(monthlySalary)
                : Money.ZERO;
    }

    @Override
    public Money calculateBenefits() {
        return FOOD_ALLOWANCE;
    }
}
