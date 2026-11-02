package application.dto;

public sealed interface EmployeeInput
        permits SalariedInput, HourlyInput, CommissionInput, TemporaryInput {
    String name();
}