package application.factory;

import application.data.FieldKind;
import application.data.PayrollData;
import application.data.PayrollField;
import application.model.EmployeeType;
import domain.service.PayrollCalculator;
import domain.service.SalariedPayrollCalculator;
import domain.vo.Seniority;

import java.util.List;

public final class SalariedCalculatorFactory implements PayrollCalculatorFactory {

    private static final String SENIORITY = "seniorityYears";

    @Override
    public EmployeeType supportedType() {
        return EmployeeType.SALARIED;
    }

    @Override
    public List<PayrollField> requiredFields() {
        return List.of(new PayrollField(SENIORITY, "Años de antigüedad", FieldKind.NUMBER));
    }

    @Override
    public PayrollCalculator create(PayrollData data) {
        return new SalariedPayrollCalculator(
                CompanyRates.BASE_SALARY,
                new Seniority(data.shortValue(SENIORITY)));
    }
}