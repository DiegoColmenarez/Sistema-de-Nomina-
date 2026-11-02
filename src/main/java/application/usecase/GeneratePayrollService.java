package application.usecase;

import application.dto.EmployeeInput;
import application.dto.PayrollResult;
import application.factory.PayrollCalculatorFactory;
import domain.model.Employee;
import domain.vo.Payroll;
import java.util.Objects;
import java.util.UUID;

public final class GeneratePayrollService implements GeneratePayrollUseCase {

    private final PayrollCalculatorFactory factory;

    public GeneratePayrollService(PayrollCalculatorFactory factory) {
        this.factory = Objects.requireNonNull(factory);
    }

    @Override
    public PayrollResult execute(EmployeeInput input) {
        Employee employee = new Employee(UUID.randomUUID(), input.name(), factory.create(input));
        Payroll payroll = employee.generatePayroll();

        return new PayrollResult(employee.name(), payroll.gross(), payroll.bonuses(),
                payroll.benefits(), payroll.deductions(), payroll.net());
    }
}