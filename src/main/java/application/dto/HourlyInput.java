package application.dto;

public record HourlyInput(String name, int hoursWorked, int seniorityYears,
                          boolean acceptsSavingsFund) implements EmployeeInput { }