package infrastructure.cli;

import java.math.BigDecimal;
import java.util.Scanner;

/** Encapsula la lectura por teclado para que el resto de la consola no dependa de Scanner. */
public final class ConsoleReader {

    private final Scanner scanner = new Scanner(System.in);

    String ask(String label) {
        System.out.print(label + ": ");
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "0";
    }

    boolean askYesNo(String label) {
        return ask(label + " (s/n)").equalsIgnoreCase("s");
    }

    int askInt(String label) {
        try {
            return Integer.parseInt(askRequired(label));
        } catch (NumberFormatException e) {
            throw InvalidConsoleInputException.notANumber(label);
        }
    }

    BigDecimal askDecimal(String label) {
        try {
            return new BigDecimal(askRequired(label));
        } catch (NumberFormatException e) {
            throw InvalidConsoleInputException.notANumber(label);
        }
    }

    private String askRequired(String label) {
        String value = ask(label);
        if (value.isBlank()) {
            throw InvalidConsoleInputException.missing(label);
        }
        return value;
    }
}
