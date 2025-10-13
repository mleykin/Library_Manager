public interface Constants {
    // Числовые константы
    public static final int UNKNOWN_NUMBER = -1;
    public static final int MIN_DATE = 0;
    public static final int MAX_DATE = 2025;

    // Константы, связанные с файлами
    public static final String FILE_EXTENSION = ".txt";
    public static final String UNKNOWN_VALUE = "unknown";
    public static final String TERMINATE_SYMBOL = "#";

    // Сообщения для ввода данных
    public static final String PROMPT_INSERT_NAME = "Enter book name:";
    public static final String PROMPT_INSERT_AUTHOR = "Enter author name:";
    public static final String PROMPT_INSERT_GENRE = "Enter genre:";
    public static final String PROMPT_INSERT_DATE = "Enter writing date (or type 'unknown'):";
    public static final String PROMPT_INSERT_FILENAME = "Enter filename (e.g., 'library.txt') or '#' to skip:";
    public static final String PROMPT_INSERT_ATTRIBUTE = "Choose attribute to search by (name, author, genre, date):";

    // Приветствие
    public static final String INTRO_INFO =
            "===> Welcome to Library Manager\n" +
                    "===> You can create, edit, search, save and load books.\n" +
                    "===> To exit any process, type '#'.\n\n" +
                    "===> Please start by loading a collection file.";

    // Справка
    public static final String HELP_INFO =
            "* Available commands:\n" +
                    "  - create : Add a new book\n" +
                    "  - list   : Show all books\n" +
                    "  - find   : Search books by attribute\n" +
                    "  - edit   : Edit a book\n" +
                    "  - save   : Save collection to file\n" +
                    "  - load   : Load collection from file\n" +
                    "  - clear  : Clear the collection\n" +
                    "  - help/? : Show this help message";

    // Команды пользователя
    public static final String CREATE = "create";
    public static final String LIST = "list";
    public static final String FIND = "find";
    public static final String EDIT = "edit";
    public static final String SAVE = "save";
    public static final String LOAD = "load";
    public static final String CLEAR = "clear";
    public static final String HELP = "help";
    public static final String HELP_ALT = "?";

    // Сообщения об ошибках
    public static final String ERROR_COLLECTION_EMPTY = "Collection is empty.";
    public static final String ERROR_DUPLICATES = "This change would create a duplicate book.";
    public static final String ERROR_WRONG_PARAMETER = "Invalid attribute. Please try again.";
    public static final String ERROR_INVALID_GENRE = "Invalid genre. Please enter again:";
    public static final String ERROR_INVALID_DATE = "Invalid date. Please enter again:";
    public static final String ERROR_EMPTY_STRING = "Input cannot be empty. Please enter again:";
    public static final String ERROR_BOOK_ALREADY_EXISTS = "This book already exists in the collection.";
    public static final String ERROR_ILLEGAL_EXTENSION = "Invalid file extension. Use '.txt' only.";
    public static final String ERROR_UNKNOWN_COMMAND = "Unknown command. Type 'help' to see available options.";

    // Информационные и успешные сообщения
    public static final String SUCCESS = "* Operation completed successfully.";
    public static final String NO_BOOKS = "No matching books found.";

    // Стилизованные методы вывода
    public static void printError(String msg) {
        System.out.println("* ERROR: " + msg);
    }

    public static void printSuccess(String msg) {
        System.out.println("* SUCCESS: " + msg);
    }

    public static void printInfo(String msg) {
        System.out.println("* " + msg);
    }
}
