package domain.model;

import domain.service.PayrollCalculator;

import domain.vo.Money;
import domain.vo.Payroll;
import domain.vo.Percentage;

import java.util.Objects;
import java.util.UUID;

public final class Employee {

    private static final Percentage PENSION_RATE = new Percentage(4);

    private final UUID id;
    private final String name;
    private final PayrollCalculator calculator;

    public Employee(UUID id, String name, PayrollCalculator calculator) {
        this.id = Objects.requireNonNull(id, "El id no puede ser nulo.");
        this.name = Objects.requireNonNull(name, "El nombre no puede ser nulo.");
        this.calculator = Objects.requireNonNull(calculator, "El calculador no puede ser nulo.");
    }

    public Payroll generatePayroll() {
        Money gross = calculator.calculateGrossSalary();
        Money bonuses = calculator.calculateBonuses();
        Money benefits = calculator.calculateBenefits();

        Money pensionDeduction = PENSION_RATE.applyTo(gross.add(bonuses));

        return Payroll.create(id, gross, bonuses, benefits, pensionDeduction);
    }

    public UUID id() { return id; }
    public String name() { return name; }
}
