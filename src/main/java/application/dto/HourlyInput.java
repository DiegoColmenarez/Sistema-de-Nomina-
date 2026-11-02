package application.dto;

import java.math.BigDecimal;

public record HourlyInput(String name, BigDecimal hourlyRate, int hoursWorked,
                          int seniorityYears, boolean acceptsSavingsFund)
        implements EmployeeInput { }