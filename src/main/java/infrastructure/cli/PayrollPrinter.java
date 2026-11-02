package infrastructure.cli;

import application.data.PayrollField;
import application.model.EmployeeRecord;
import domain.vo.Payroll;

import java.util.List;

final class PayrollPrinter {

    void printEmployees(List<EmployeeRecord> employees) {
        System.out.println("\n=== EMPLEADOS ===");
        for (int i = 0; i < employees.size(); i++) {
            EmployeeRecord employee = employees.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, employee.name(), employee.type().label());
        }
    }

    void printRequiredData(EmployeeRecord employee, List<PayrollField> fields) {
        System.out.printf("%n=== %s - Empleado %s ===%n", employee.name(), employee.type().label());
        System.out.println("Datos requeridos:");
        fields.forEach(field -> System.out.println(" - " + field.label()));
        System.out.println();
    }

    void printPayroll(EmployeeRecord employee, Payroll payroll) {
        System.out.printf("%n--- Nómina de %s ---%n", employee.name());
        System.out.println("Salario bruto: " + payroll.gross());
        System.out.println("Bonos:         " + payroll.bonuses());
        System.out.println("Beneficios:    " + payroll.benefits());
        System.out.println("Deducciones:   " + payroll.deductions());
        System.out.println("Salario neto:  " + payroll.net());
    }

    void printError(String message) {
        System.out.println("Dato inválido: " + message);
    }

    void printInvalidOption() {
        System.out.println("Opción no válida.");
    }
}