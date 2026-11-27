package com.nomina.application.usecase;

import com.nomina.application.dto.EmployeeContract;
import com.nomina.application.dto.PayrollResult;
import com.nomina.application.dto.command.GeneratePayrollCommand;
import com.nomina.application.factory.PayrollCalculatorFactory;
import com.nomina.application.ports.out.EmployeeDataPort;
import com.nomina.application.ports.out.SavePayrollPort;
import com.nomina.domain.exceptions.EmployeeNotFoundException;
import com.nomina.domain.model.Employee;
import com.nomina.domain.vo.Payroll;
import java.util.Objects;

public final class GeneratePayrollService implements GeneratePayrollUseCase {

    private final EmployeeDataPort employees;
    private final SavePayrollPort storage;
    private final PayrollCalculatorFactory factory;

    public GeneratePayrollService(EmployeeDataPort employees, SavePayrollPort storage,
                                  PayrollCalculatorFactory factory) {
        this.employees = Objects.requireNonNull(employees);
        this.storage = Objects.requireNonNull(storage);
        this.factory = Objects.requireNonNull(factory);
    }

    @Override
    public PayrollResult execute(GeneratePayrollCommand command) {
        EmployeeContract contract = employees.findById(command.employeeId())
                .orElseThrow(() -> new EmployeeNotFoundException(command.employeeId()));

        Employee employee = new Employee(contract.employeeId(), contract.name(),
                factory.create(contract, command));
        Payroll payroll = employee.generatePayroll();

        PayrollResult result = new PayrollResult(employee.id(), employee.name(), command.period(),
                payroll.gross(), payroll.bonuses(), payroll.benefits(),
                payroll.deductions(), payroll.net());

        storage.save(result);
        return result;
    }
}