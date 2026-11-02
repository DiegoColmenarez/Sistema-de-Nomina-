package application.factory;

import application.data.PayrollData;
import application.data.PayrollField;
import application.model.EmployeeType;
import domain.service.PayrollCalculator;
import domain.service.TemporaryPayrollCalculator;

import java.util.List;

public final class TemporaryCalculatorFactory implements PayrollCalculatorFactory {

    @Override
    public EmployeeType supportedType() {
        return EmployeeType.TEMPORARY;
    }

    @Override
    public List<PayrollField> requiredFields() {
        return List.of();
    }

    @Override
    public PayrollCalculator create(PayrollData data) {
        return new TemporaryPayrollCalculator(CompanyRates.BASE_SALARY);
    }
}