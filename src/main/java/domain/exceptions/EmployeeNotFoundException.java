package domain.exceptions;

import java.util.UUID;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(UUID employeeId) {
        super("No existe un empleado con id " + employeeId);
    }
}
