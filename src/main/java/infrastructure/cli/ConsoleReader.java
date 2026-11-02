package infrastructure.cli;

import java.util.Scanner;

final class ConsoleReader {

    private final Scanner scanner = new Scanner(System.in);

    String ask(String label) {
        System.out.print(label + ": ");
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "0";
    }

    boolean askYesNo(String label) {
        return ask(label + " (s/n)").equalsIgnoreCase("s");
    }
}