package application.usecase;

import application.dto.EmployeeInput;
import application.dto.PayrollResult;

public interface GeneratePayrollUseCase {
    PayrollResult execute(EmployeeInput input);
}