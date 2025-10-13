import java.util.*;

public class Main implements Constants {
    public static void main(String[] args) {
        // Создаём сканер для чтения пользовательского ввода
        Scanner scan = new Scanner(System.in);

        // Выводим приветственное сообщение
        System.out.println(INTRO_INFO);

        // Основная коллекция книг
        LinkedList<Book> collection = new LinkedList<>();

        // Временный список для найденных книг
        LinkedList<Book> founded = new LinkedList<>();

        // Создаём менеджер, который будет обрабатывать команды
        Manager manager = new Manager(scan);

        // Команда пользователя
        String command = "";

        // Загружаем коллекцию из файла при запуске
        manager.loadFromFile(collection);

        // Обработка команд
        while (!command.equals(TERMINATE_SYMBOL)) {
            System.out.print("> ");
            command = scan.nextLine().trim();
            founded.clear(); // Очищаем список найденных книг перед каждой командой

            // Обработка команды
            switch (command.toLowerCase()) {
                case TERMINATE_SYMBOL:
                    // Если пользователь ввёл символ завершения — пропускаем
                    continue;

                case HELP:
                case HELP_ALT:
                    // Выводим справку
                    manager.help();
                    break;

                case CREATE:
                    // Создаём новую книгу
                    Constants.printInfo("Creating new book...");
                    Book newBook = manager.create();

                    // Проверяем, есть ли такая книга уже в коллекции
                    if (manager.findDuplicates(collection, newBook)) {
                        Constants.printError(ERROR_BOOK_ALREADY_EXISTS);
                    } else {
                        collection.add(newBook); // Добавляем книгу
                    }
                    break;

                case LIST:
                    // Показываем все книги
                    Constants.printInfo("Listing books...");
                    manager.list(collection);
                    break;

                case FIND:
                    // Если в коллекции одна книга — показываем её
                    if (collection.size() == 1) {
                        collection.getFirst().info();
                    }
                    // Если книг больше — запускаем поиск
                    else if (collection.size() > 1) {
                        Constants.printInfo("Searching books...");
                        manager.find(collection, founded);
                    }
                    break;

                case EDIT:
                    // Редактируем книгу
                    Constants.printInfo("Editing book...");
                    Book editedBook = new Book();

                    if (!collection.isEmpty()) {
                        // Если одна книга — редактируем её
                        if (collection.size() == 1) {
                            editedBook = collection.getFirst();
                        }
                        // Если несколько — ищем нужную
                        else {
                            editedBook = manager.findOneOnlyBook(collection);
                        }

                        // Если книга найдена — редактируем
                        if (editedBook != null) {
                            manager.edit(editedBook, collection);
                        }
                    } else {
                        Constants.printError(ERROR_COLLECTION_EMPTY);
                    }
                    break;

                case SAVE:
                    // Сохраняем коллекцию в файл
                    Constants.printInfo("Saving collection...");
                    manager.saveToFile(collection);
                    break;

                case LOAD:
                    // Загружаем коллекцию из файла
                    Constants.printInfo("Loading collection...");
                    manager.loadFromFile(collection);
                    break;

                case CLEAR:
                    // Очищаем коллекцию
                    if (!collection.isEmpty()) {
                        collection.clear();
                        Constants.printSuccess("Collection cleared.");
                    } else {
                        Constants.printError(ERROR_COLLECTION_EMPTY);
                    }
                    break;

                default:
                    // Обработка неизвестной команды
                    Constants.printError(ERROR_UNKNOWN_COMMAND);
            }
        }

        // Перед завершением предложение сохранить коллекцию
        Constants.printInfo("Before terminating, save your collection.");
        if (!collection.isEmpty()) {
            String answer = manager.readNonEmptyString("Do you want to save changes? (y/n)");
            if (answer.equalsIgnoreCase("y")) {
                manager.saveToFile(collection);
            }
        } else {
            Constants.printError(ERROR_COLLECTION_EMPTY);
        }

        // Закрываем сканер и завершаем программу
        scan.close();
        Constants.printSuccess("Program terminated successfully.");
    }
}
