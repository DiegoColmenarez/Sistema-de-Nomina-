package application.dto.command;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

public record GeneratePayrollCommand(UUID employeeId, YearMonth period,
                                     Integer hoursWorked, BigDecimal sales) {}