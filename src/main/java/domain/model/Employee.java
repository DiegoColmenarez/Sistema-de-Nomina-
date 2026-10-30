package domain.model;

import domain.service.PayrollCalculator;
import domain.vo.MandatoryDeductions;
import domain.vo.Money;
import domain.vo.Payroll;
import java.util.Objects;
import java.util.UUID;

public final class Employee {

    private final UUID id;
    private final String name;
    private final PayrollCalculator calculator;

    public Employee(UUID id, String name, PayrollCalculator calculator) {
        this.id = Objects.requireNonNull(id, "El id no puede ser nulo.");
        this.name = Objects.requireNonNull(name, "El nombre no puede ser nulo.");
        this.calculator = Objects.requireNonNull(calculator, "El calculador no puede ser nulo.");
    }

    public Payroll generatePayroll(MandatoryDeductions deductions) {
        Money gross = calculator.calculateGrossSalary();
        Money bonuses = calculator.calculateBonuses();
        Money benefits = calculator.calculateBenefits();

        Money deductionAmount = deductions.calculateOn(gross.add(bonuses));

        return Payroll.create(id, gross, bonuses, benefits, deductionAmount);
    }

    public UUID id() { return id; }
    public String name() { return name; }
}
