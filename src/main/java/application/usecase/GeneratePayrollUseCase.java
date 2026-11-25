package application.usecase;

import application.dto.PayrollResult;
import application.dto.command.GeneratePayrollCommand;

public interface GeneratePayrollUseCase {
    PayrollResult execute(GeneratePayrollCommand command);
}