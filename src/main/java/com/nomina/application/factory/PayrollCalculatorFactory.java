package com.nomina.application.factory;

import application.dto.*;
import com.nomina.application.dto.*;
import com.nomina.application.dto.command.GeneratePayrollCommand;
import com.nomina.domain.exceptions.InvalidHoursWorkedException;
import com.nomina.domain.service.*;
import com.nomina.domain.vo.HoursWorked;
import com.nomina.domain.vo.Sales;
import com.nomina.domain.vo.Seniority;
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