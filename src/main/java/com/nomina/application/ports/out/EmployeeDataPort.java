package com.nomina.application.ports.out;

import com.nomina.application.dto.EmployeeContract;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeDataPort {
    Optional<EmployeeContract> findById(UUID employeeId);
}