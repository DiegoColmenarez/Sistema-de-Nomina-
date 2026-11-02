package application.factory;

import application.data.FieldKind;
import application.data.PayrollData;
import application.data.PayrollField;
import application.model.EmployeeType;
import domain.service.CommissionPayrollCalculator;
import domain.service.PayrollCalculator;

import java.util.List;

public final class CommissionCalculatorFactory implements PayrollCalculatorFactory {

    private static final String SALES = "sales";

    @Override
    public EmployeeType supportedType() {
        return EmployeeType.COMMISSION;
    }

    @Override
    public List<PayrollField> requiredFields() {
        return List.of(new PayrollField(SALES, "Ventas del mes", FieldKind.NUMBER));
    }

    @Override
    public PayrollCalculator create(PayrollData data) {
        return new CommissionPayrollCalculator(CompanyRates.BASE_SALARY, data.sales(SALES));
    }
}