package ru.leti.wikisearch;

import java.util.Scanner;

// отвечает только за ввод с клавиатуры
public class ConsoleInput {

    // команда для выхода из программы
    public static final String EXIT_COMMAND = "выход";

    private final Scanner scanner;

    public ConsoleInput(Scanner scanner) {
        this.scanner = scanner;
    }

    // читаем запрос, пока не введут что-то непустое, null значит выход
    public String readQuery() {
        while (true) {
            System.out.print("\nвведите запрос (или \"" + EXIT_COMMAND + "\"): ");
            if (!scanner.hasNextLine()) {
                return null; // ввод закончился
            }
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase(EXIT_COMMAND)) {
                return null;
            }
            if (line.isEmpty()) {
                System.out.println("запрос пустой, попробуйте еще раз");
                continue;
            }
            return line;
        }
    }

    // читаем номер статьи от 1 до max, 0 значит новый поиск
    public int readChoice(int max) {
        while (true) {
            System.out.print("\nномер статьи (1-" + max + "), 0 - новый поиск: ");
            if (!scanner.hasNextLine()) {
                return 0; // ввод закончился
            }
            String line = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(line);
                if (number >= 0 && number <= max) {
                    return number;
                }
            } catch (NumberFormatException ignored) {
                // это не число, ниже скажем об ошибке
            }
            System.out.println("нужно ввести число от 0 до " + max);
        }
    }
}
