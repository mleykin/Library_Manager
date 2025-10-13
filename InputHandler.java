import java.util.Scanner;

// Абстрактный класс обработки пользовательского ввода (оборачивает валидатор текста с необходимым форматом)
public abstract class InputHandler implements Constants {
    protected final Scanner scanner;
    protected final InputValidator validator;

    // Конструктор принимает сканер и создаёт валидатор
    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
        this.validator = new InputValidator();
    }

    // Чтение непустой строки
    protected String readNonEmptyString(String message) {
        Constants.printInfo(message);
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            Constants.printError(ERROR_EMPTY_STRING);
        }
    }

    // Чтение и проверка жанра
    protected Genres readGenre() {
        while (true) {
            String input = readNonEmptyString(PROMPT_INSERT_GENRE);
            if (validator.validate(input, Attributes.genre))
                return Genres.valueOf(input.trim().toLowerCase());
        }
    }

    // Чтение и проверка даты
    protected int readDate() {
        while (true) {
            String input = readNonEmptyString(PROMPT_INSERT_DATE);
            if (input.equals(UNKNOWN_VALUE)) return UNKNOWN_NUMBER;
            if (validator.validate(input, Attributes.date))
                return Integer.parseInt(input);
        }
    }

    // Чтение и проверка названия атрибута
    protected String readAttribute() {
        while (true) {
            String input = readNonEmptyString(PROMPT_INSERT_ATTRIBUTE);
            if (validator.validate(input, Attributes.attribute)) return input;
        }
    }

    // Чтение имени файла и проверка расширения
    protected String readFileName() {
        while (true) {
            String input = readNonEmptyString(PROMPT_INSERT_FILENAME);
            if (input.equals(TERMINATE_SYMBOL)) return input;
            if (validator.validate(input, Attributes.extension)) return input;
        }
    }
}
