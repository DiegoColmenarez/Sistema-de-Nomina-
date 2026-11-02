package application.dto;

import java.math.BigDecimal;

public record TemporaryInput(String name, BigDecimal monthlySalary)
        implements EmployeeInput { }