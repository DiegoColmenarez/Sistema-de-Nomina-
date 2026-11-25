package application.dto;

import domain.vo.Money;
import java.util.UUID;

public record TemporaryContract(UUID employeeId, String name, Money monthlySalary)
        implements EmployeeContract {}