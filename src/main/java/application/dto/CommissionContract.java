package application.dto;

import domain.vo.Money;
import java.util.UUID;

public record CommissionContract(UUID employeeId, String name, Money baseSalary)
        implements EmployeeContract {}