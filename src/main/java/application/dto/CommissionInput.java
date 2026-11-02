package application.dto;

import java.math.BigDecimal;

public record CommissionInput(String name, BigDecimal baseSalary, BigDecimal sales,
                              BigDecimal commissionRate)
        implements EmployeeInput { }
