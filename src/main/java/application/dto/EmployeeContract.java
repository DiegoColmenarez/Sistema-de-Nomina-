package application.dto;

import java.util.UUID;

public sealed interface EmployeeContract
        permits CommissionContract, HourlyContract, SalariedContract, TemporaryContract {
    UUID employeeId();
    String name();
}
