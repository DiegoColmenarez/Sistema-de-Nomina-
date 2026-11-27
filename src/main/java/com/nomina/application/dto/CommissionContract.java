package com.nomina.application.dto;

import com.nomina.domain.vo.Money;
import java.util.UUID;

public record CommissionContract(UUID employeeId, String name, Money baseSalary)
        implements EmployeeContract {}