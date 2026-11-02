package application.dto;

import java.math.BigDecimal;

public record SalariedInput(String name, BigDecimal monthlySalary, int seniorityYears)
        implements EmployeeInput { }