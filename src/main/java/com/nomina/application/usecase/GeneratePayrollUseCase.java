package com.nomina.application.usecase;

import com.nomina.application.dto.PayrollResult;
import com.nomina.application.dto.command.GeneratePayrollCommand;

public interface GeneratePayrollUseCase {
    PayrollResult execute(GeneratePayrollCommand command);
}