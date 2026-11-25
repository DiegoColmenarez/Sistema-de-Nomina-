package application.usecase;

import application.dto.EmployeeContract;
import application.dto.PayrollResult;

public interface GeneratePayrollUseCase {
    PayrollResult execute(EmployeeContract input);
}