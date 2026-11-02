package application.factory;

import application.dto.*;
import domain.service.*;
import domain.vo.*;

public final class PayrollCalculatorFactory {

    public PayrollCalculator create(EmployeeInput input) {
        return switch (input) {
            case SalariedInput i -> new SalariedPayrollCalculator(
                    new Money(i.monthlySalary()),
                    new Seniority((short) i.seniorityYears()));

            case HourlyInput i -> new HourlyPayrollCalculator(
                    new Money(i.hourlyRate()),
                    new HoursWorked(i.hoursWorked()),
                    new Seniority((short) i.seniorityYears()),
                    i.acceptsSavingsFund());

            case CommissionInput i -> new CommissionPayrollCalculator(
                    new Money(i.baseSalary()),
                    new Sales(i.sales()),
                    new Percentage(i.commissionRate()));

            case TemporaryInput i -> new TemporaryPayrollCalculator(
                    new Money(i.monthlySalary()));
        };
    }
}