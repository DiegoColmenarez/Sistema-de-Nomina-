package application.factory;

import application.dto.*;
import application.dto.command.GeneratePayrollCommand;
import domain.exceptions.InvalidHoursWorkedException;
import domain.service.*;
import domain.vo.*;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

public final class PayrollCalculatorFactory {

    public PayrollCalculator create(EmployeeContract contract, GeneratePayrollCommand command) {
        return switch (contract) {
            case SalariedContract c -> new SalariedPayrollCalculator(
                    c.monthlySalary(),
                    seniority(c.hireDate(), command.period()));

            case HourlyContract c -> new HourlyPayrollCalculator(
                    c.hourlyRate(),
                    hoursWorked(command),
                    seniority(c.hireDate(), command.period()),
                    c.acceptsSavingsFund());

            case CommissionContract c -> new CommissionPayrollCalculator(
                    c.baseSalary(),
                    new Sales(command.sales()));

            case TemporaryContract c -> new TemporaryPayrollCalculator(c.monthlySalary());
        };
    }

    private Seniority seniority(java.time.LocalDate hireDate, YearMonth period) {
        long years = ChronoUnit.YEARS.between(hireDate, period.atEndOfMonth());
        return new Seniority((short) years);
    }

    private HoursWorked hoursWorked(GeneratePayrollCommand command) {
        if (command.hoursWorked() == null) {
            throw InvalidHoursWorkedException.nullValue();
        }
        return new HoursWorked(command.hoursWorked());
    }
}