package infrastructure.cli;

import application.data.FieldKind;
import application.data.PayrollData;
import application.data.PayrollField;
import application.model.EmployeeRecord;
import application.usecase.GeneratePayrollUseCase;
import application.usecase.GetRequiredFieldsUseCase;
import application.usecase.ListEmployeesUseCase;
import domain.exceptions.DomainException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class PayrollConsole {

    private static final String EXIT = "0";

    private final ListEmployeesUseCase listEmployees;
    private final GetRequiredFieldsUseCase getRequiredFields;
    private final GeneratePayrollUseCase generatePayroll;
    private final ConsoleReader reader = new ConsoleReader();
    private final PayrollPrinter printer = new PayrollPrinter();

    public PayrollConsole(ListEmployeesUseCase listEmployees,
                          GetRequiredFieldsUseCase getRequiredFields,
                          GeneratePayrollUseCase generatePayroll) {
        this.listEmployees = listEmployees;
        this.getRequiredFields = getRequiredFields;
        this.generatePayroll = generatePayroll;
    }

    public void run() {
        List<EmployeeRecord> employees = listEmployees.execute();
        boolean running = true;

        while (running) {
            printer.printEmployees(employees);
            String input = reader.ask("Seleccione un empleado (0 para salir)");

            if (input.equals(EXIT)) {
                running = false;
            } else {
                pickEmployee(employees, input).ifPresentOrElse(
                        this::calculatePayroll,
                        printer::printInvalidOption);
            }
        }
    }

    private Optional<EmployeeRecord> pickEmployee(List<EmployeeRecord> employees, String input) {
        try {
            int number = Integer.parseInt(input);
            return number >= 1 && number <= employees.size()
                    ? Optional.of(employees.get(number - 1))
                    : Optional.empty();
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private void calculatePayroll(EmployeeRecord employee) {
        List<PayrollField> fields = getRequiredFields.execute(employee.type());
        printer.printRequiredData(employee, fields);
        boolean retry;

        do {
            retry = false;
            try {
                var payroll = generatePayroll.execute(employee.id(), askData(fields));
                printer.printPayroll(employee, payroll);
            } catch (DomainException e) {
                printer.printError(e.getMessage());
                retry = reader.askYesNo("¿Desea intentarlo de nuevo?");
            }
        } while (retry);
    }

    private PayrollData askData(List<PayrollField> fields) {
        Map<String, String> answers = new LinkedHashMap<>();
        for (PayrollField field : fields) {
            answers.put(field.key(), reader.ask(promptFor(field)));
        }
        return new PayrollData(answers);
    }

    private String promptFor(PayrollField field) {
        return field.kind() == FieldKind.YES_NO ? field.label() + " (s/n)" : field.label();
    }
}