package infrastructure.cli;

import application.dto.*;
import application.usecase.GeneratePayrollUseCase;
import domain.exceptions.DomainException;

import java.util.Objects;

public final class PayrollConsole {

    private static final String EXIT = "0";

    private final GeneratePayrollUseCase generatePayroll;
    private final ConsoleReader reader = new ConsoleReader();
    private final PayrollPrinter printer = new PayrollPrinter();

    public PayrollConsole(GeneratePayrollUseCase generatePayroll) {
        this.generatePayroll = Objects.requireNonNull(generatePayroll, "El caso de uso no puede ser nulo.");
    }

    public void run() {
        boolean running = true;

        while (running) {
            printer.printMenu();
            String option = reader.ask("Seleccione una opción");

            if (option.equals(EXIT)) {
                running = false;
            } else {
                try {
                    EmployeeInput input = switch (option) {
                        case "1" -> new SalariedInput(
                                reader.ask("Nombre del empleado"),
                                reader.askInt("Años de antigüedad")
                        );
                        case "2" -> new HourlyInput(
                                reader.ask("Nombre del empleado"),
                                reader.askInt("Horas trabajadas"),
                                reader.askInt("Años de antigüedad"),
                                reader.askYesNo("¿Acepta el fondo de ahorro?")
                        );
                        case "3" -> new CommissionInput(
                                reader.ask("Nombre del empleado"),
                                reader.askDecimal("Ventas del mes")
                        );
                        case "4" -> new TemporaryInput(
                                reader.ask("Nombre del empleado")
                        );
                        default -> throw new IllegalArgumentException("Opción no válida.");
                    };

                    var result = generatePayroll.execute(input);
                    printer.printPayroll(result);

                } catch (DomainException | IllegalArgumentException | InvalidConsoleInputException e) {
                    printer.printError(e.getMessage());
                }
            }
        }
    }
}