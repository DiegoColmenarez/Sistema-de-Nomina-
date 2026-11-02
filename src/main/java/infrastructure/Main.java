package infrastructure;

import application.factory.CommissionCalculatorFactory;
import application.factory.HourlyCalculatorFactory;
import application.factory.PayrollCalculatorRegistry;
import application.factory.SalariedCalculatorFactory;
import application.factory.TemporaryCalculatorFactory;
import application.model.EmployeeRecord;
import application.model.EmployeeType;
import application.usecase.GeneratePayrollUseCase;
import application.usecase.GetRequiredFieldsUseCase;
import application.usecase.ListEmployeesUseCase;
import infrastructure.cli.PayrollConsole;
import infrastructure.persistence.InMemoryEmployeeRepository;

import java.util.List;
import java.util.UUID;

public final class Main {

    public static void main(String[] args) {
        var repository = new InMemoryEmployeeRepository(List.of(
                new EmployeeRecord(UUID.randomUUID(), "Ana Gómez", EmployeeType.SALARIED),
                new EmployeeRecord(UUID.randomUUID(), "Luis Pérez", EmployeeType.HOURLY),
                new EmployeeRecord(UUID.randomUUID(), "Marta Ruiz", EmployeeType.COMMISSION),
                new EmployeeRecord(UUID.randomUUID(), "Carlos Díaz", EmployeeType.TEMPORARY)));

        var registry = new PayrollCalculatorRegistry(List.of(
                new SalariedCalculatorFactory(),
                new HourlyCalculatorFactory(),
                new CommissionCalculatorFactory(),
                new TemporaryCalculatorFactory()));

        new PayrollConsole(
                new ListEmployeesUseCase(repository),
                new GetRequiredFieldsUseCase(registry),
                new GeneratePayrollUseCase(repository, registry)
        ).run();
    }
}