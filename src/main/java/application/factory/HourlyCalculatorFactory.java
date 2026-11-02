package application.factory;

import application.data.FieldKind;
import application.data.PayrollData;
import application.data.PayrollField;
import application.model.EmployeeType;
import domain.service.HourlyPayrollCalculator;
import domain.service.PayrollCalculator;
import domain.vo.HoursWorked;
import domain.vo.Seniority;

import java.util.List;

public final class HourlyCalculatorFactory implements PayrollCalculatorFactory {

    private static final String HOURS_WORKED = "hoursWorked";
    private static final String SENIORITY = "seniorityYears";
    private static final String ACCEPTS_SAVINGS_FUND = "acceptsSavingsFund";

    @Override
    public EmployeeType supportedType() {
        return EmployeeType.HOURLY;
    }

    @Override
    public List<PayrollField> requiredFields() {
        return List.of(
                new PayrollField(HOURS_WORKED, "Horas trabajadas", FieldKind.NUMBER),
                new PayrollField(SENIORITY, "Años de antigüedad", FieldKind.NUMBER),
                new PayrollField(ACCEPTS_SAVINGS_FUND, "¿Acepta el fondo de ahorro?", FieldKind.YES_NO));
    }

    @Override
    public PayrollCalculator create(PayrollData data) {
        return new HourlyPayrollCalculator(
                CompanyRates.HOURLY_RATE,
                new HoursWorked(data.integer(HOURS_WORKED)),
                new Seniority(data.shortValue(SENIORITY)),
                data.yesNo(ACCEPTS_SAVINGS_FUND));
    }
}