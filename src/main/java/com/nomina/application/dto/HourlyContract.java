package com.nomina.application.dto;

import com.nomina.domain.vo.Money;
import java.time.LocalDate;
import java.util.UUID;

public record HourlyContract(UUID employeeId, String name, LocalDate hireDate,
                             Money hourlyRate, boolean acceptsSavingsFund)
        implements EmployeeContract {}