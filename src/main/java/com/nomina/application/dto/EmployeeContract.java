package com.nomina.application.dto;

import java.util.UUID;

public sealed interface EmployeeContract
        permits SalariedContract, HourlyContract, CommissionContract, TemporaryContract {
    UUID employeeId();
    String name();
}
