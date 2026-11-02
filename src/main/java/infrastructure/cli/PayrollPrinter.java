package infrastructure.cli;

import application.dto.PayrollResult;

final class PayrollPrinter {

    void printMenu() {
        System.out.println("\n=== SISTEMA DE NÓMINA ===");
        System.out.println("1. Empleado Asalariado");
        System.out.println("2. Empleado por Horas");
        System.out.println("3. Empleado por Comisión");
        System.out.println("4. Empleado Temporal");
        System.out.println("0. Salir");
    }

    void printPayroll(PayrollResult result) {
        System.out.printf("%n--- Nómina de %s ---%n", result.employeeName());
        System.out.println("Salario bruto: " + result.gross());
        System.out.println("Bonos:         " + result.bonuses());
        System.out.println("Beneficios:    " + result.benefits());
        System.out.println("Deducciones:   " + result.deductions());
        System.out.println("Salario neto:  " + result.net());
    }

    void printError(String message) {
        System.out.println("Error: " + message);
    }
}
