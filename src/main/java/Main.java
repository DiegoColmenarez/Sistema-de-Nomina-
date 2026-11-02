import application.factory.PayrollCalculatorFactory;
import application.usecase.GeneratePayrollService;
import infrastructure.cli.PayrollConsole;

public final class Main {

    public static void main(String[] args) {
        var factory = new PayrollCalculatorFactory();
        var generatePayroll = new GeneratePayrollService(factory);

        new PayrollConsole(generatePayroll).run();
    }
}
