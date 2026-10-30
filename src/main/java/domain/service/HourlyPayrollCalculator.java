package domain.service;

import domain.vo.HoursWorked;
import domain.vo.Money;
import domain.vo.Percentage;
import domain.vo.Seniority;
import java.math.BigDecimal;
import java.util.Objects;

public final class HourlyPayrollCalculator implements PayrollCalculator {

    private static final BigDecimal OVERTIME_MULTIPLIER = new BigDecimal("1.5");
    private static final Percentage SAVINGS_FUND_RATE = new Percentage(2);
    private final Money hourlyRate;
    private final HoursWorked hoursWorked;
    private final Seniority seniority;
    private final boolean acceptsSavingsFund;

    public HourlyPayrollCalculator(Money hourlyRate, HoursWorked hoursWorked,
                                   Seniority seniority, boolean acceptsSavingsFund) {
        this.hourlyRate = Objects.requireNonNull(hourlyRate, "La tarifa por hora no puede ser nula.");
        this.hoursWorked = Objects.requireNonNull(hoursWorked, "Las horas trabajadas no pueden ser nulas.");
        this.seniority = Objects.requireNonNull(seniority, "La antigüedad no puede ser nula.");
        this.acceptsSavingsFund = acceptsSavingsFund;
    }

    @Override
    public Money calculateGrossSalary() {
        return normalPay().add(overtimePay());
    }

    @Override
    public Money calculateBonuses() {
        return Money.ZERO;
    }

    @Override
    public Money calculateBenefits() {
        boolean eligible = acceptsSavingsFund && seniority.isEligibleForSavingsFund();
        return eligible
                ? SAVINGS_FUND_RATE.applyTo(calculateGrossSalary())
                : Money.ZERO;
    }

    private Money normalPay() {
        return hourlyRate.multiply(BigDecimal.valueOf(hoursWorked.getHoursNormal()));
    }

    private Money overtimePay() {
        return hourlyRate
                .multiply(BigDecimal.valueOf(hoursWorked.getOverTime()))
                .multiply(OVERTIME_MULTIPLIER);
    }
}