package application.factory;

import application.dto.*;
import domain.service.*;
import domain.vo.*;

public final class PayrollCalculatorFactory {

    private static final Money BASE_SALARY = new Money("1750905");
    private static final Money HOURLY_RATE = new Money("8338");

    public PayrollCalculator create(EmployeeInput input) {
        return switch (input) {
            case SalariedInput i -> new SalariedPayrollCalculator(
                    BASE_SALARY,
                    new Seniority((short) i.seniorityYears()));

            case HourlyInput i -> new HourlyPayrollCalculator(
                    HOURLY_RATE,
                    new HoursWorked(i.hoursWorked()),
                    new Seniority((short) i.seniorityYears()),
                    i.acceptsSavingsFund());

            case CommissionInput i -> new CommissionPayrollCalculator(
                    BASE_SALARY,
                    new Sales(i.sales()));

            case TemporaryInput i -> new TemporaryPayrollCalculator(BASE_SALARY);
        };
    }
}