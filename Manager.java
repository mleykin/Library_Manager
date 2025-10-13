import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Manager extends InputHandler implements Constants {
    public Manager(Scanner scanner) {
        super(scanner);
    }

    // Вывод справки по доступным командам
    public void help() {
        Constants.printInfo(HELP_INFO);
    }

    // Создание новой книги с вводом данных
    public Book create() {
        String name = readNonEmptyString(PROMPT_INSERT_NAME);
        String author = readNonEmptyString(PROMPT_INSERT_AUTHOR);
        Genres genre = readGenre();
        int date = readDate();
        Constants.printSuccess("Book created.");
        return new Book(name, author, genre, date);
    }

    // Вывод всех книг из коллекции
    public void list(LinkedList<Book> books) {
        if (books.isEmpty()) {
            Constants.printError(ERROR_COLLECTION_EMPTY);
        } else {
            Constants.printInfo("Books in collection:");
            for (Book book : books) book.info();
        }
    }

    // Проверка на наличие дубликата книги
    public boolean findDuplicates(LinkedList<Book> books, Book curBook) {
        for (Book book : books) {
            if (curBook.isEquals(book)) return true;
        }
        return false;
    }

    // Поиск книг по выбранному атрибуту
    public void find(LinkedList<Book> books, LinkedList<Book> founded) {
        if (books.isEmpty()) {
            Constants.printError(ERROR_COLLECTION_EMPTY);
            return;
        }

        String attribute = readAttribute();
        switch (attribute.toLowerCase()) {
            case "name": {
                String name = readNonEmptyString(PROMPT_INSERT_NAME);
                for (Book book : books) {
                    if (book.getName().equalsIgnoreCase(name)) founded.add(book);
                }
                break;
            }
            case "author": {
                String author = readNonEmptyString(PROMPT_INSERT_AUTHOR);
                for (Book book : books) {
                    if (book.getAuthor().equalsIgnoreCase(author)) founded.add(book);
                }
                break;
            }
            case "genre": {
                Genres genre = readGenre();
                for (Book book : books) {
                    if (book.getGenre().equalsIgnoreCase(genre.name())) founded.add(book);
                }
                break;
            }
            case "writing date":
            case "date": {
                int date = readDate();
                for (Book book : books) {
                    if (book.getDate() == date) founded.add(book);
                }
                break;
            }
            default:
                Constants.printError(ERROR_WRONG_PARAMETER);
                return;
        }

        if (!founded.isEmpty()) {
            for (Book curBook : founded) curBook.info();
        } else {
            Constants.printInfo(NO_BOOKS);
        }
    }

    // Поиск одной конкретной книги с уточнением атрибутов
    public Book findOneOnlyBook(LinkedList<Book> books) {
        LinkedList<Book> additional = new LinkedList<>();
        LinkedList<Book> founded = new LinkedList<>();
        Book book = new Book();
        String answer;

        do {
            Constants.printInfo("Finding one book in collection...");
            find(books, founded);

            while (founded.size() > 1) {
                Constants.printInfo("Can't recognize the book. Choose one more attribute to narrow down.");
                find(founded, additional);
                founded.clear();
                founded.addAll(additional);
                additional.clear();
            }

            if (founded.isEmpty()) {
                Constants.printInfo(NO_BOOKS);
                break;
            }

            book = founded.getFirst();
            answer = readNonEmptyString("Did you find this book? (y/n)");
            if (answer.equalsIgnoreCase("y")) {
                Constants.printSuccess("Book selected.");
                return book;
            }
        } while (answer.equalsIgnoreCase("n"));

        return null;
    }

    // Редактирование выбранной книги
    public void edit(Book book, LinkedList<Book> allBooks) {
        Book tempBook = new Book(book.getName(), book.getAuthor(),
                Genres.valueOf(book.getGenre()), book.getDate());

        String param;
        boolean valid = false;

        // Выбор корректного атрибута
        do {
            param = readAttribute();
            switch (param.toLowerCase()) {
                case "name": {
                    String input = readNonEmptyString(PROMPT_INSERT_NAME);
                    tempBook.setName(input);
                    valid = true;
                    break;
                }
                case "author": {
                    String input = readNonEmptyString(PROMPT_INSERT_AUTHOR);
                    tempBook.setAuthor(input);
                    valid = true;
                    break;
                }
                case "genre": {
                    Genres genre = readGenre();
                    tempBook.setGenre(genre);
                    valid = true;
                    break;
                }
                case "date":
                case "writing date": {
                    int date = readDate();
                    tempBook.setDate(date);
                    valid = true;
                    break;
                }
                default:
                    Constants.printError(ERROR_WRONG_PARAMETER);
            }
        } while (!valid);

        // Проверка на дубликат перед сохранением
        for (Book existingBook : allBooks) {
            if (existingBook == book) continue;
            if (tempBook.isEquals(existingBook)) {
                Constants.printError(ERROR_DUPLICATES);
                return;
            }
        }

        // Сохранение изменений
        book.setName(tempBook.getName());
        book.setAuthor(tempBook.getAuthor());
        book.setGenre(Genres.valueOf(tempBook.getGenre()));
        book.setDate(tempBook.getDate());
        Constants.printSuccess("Book updated.");
    }

    // Сохранение коллекции в файл
    public void saveToFile(LinkedList<Book> books) {
        boolean success = false;
        while (!success) {
            String filename = readFileName();
            if (filename.equals(TERMINATE_SYMBOL)) return;

            if (books.isEmpty()) {
                Constants.printInfo(ERROR_COLLECTION_EMPTY);
                String answer = readNonEmptyString("Do you want to save an empty collection? (y/n)");
                if (!answer.equalsIgnoreCase("y")) return;
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(books);
                Constants.printSuccess("Books saved: " + books.size());
                success = true;
            } catch (IOException e) {
                Constants.printError("Saving failed: " + e.getMessage());
                break;
            }
        }
    }

    // Загрузка коллекции из файла
    public void loadFromFile(LinkedList<Book> books) {
        boolean success = false;
        while (!success) {
            String filename = readFileName();
            if (filename.equals(TERMINATE_SYMBOL)) return;

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                LinkedList<Book> loadedBooks = (LinkedList<Book>) ois.readObject();
                books.clear();
                books.addAll(loadedBooks);
                Constants.printSuccess("Books loaded: " + books.size());
                success = true;
            } catch (FileNotFoundException e) {
                Constants.printError("File not found. Please try again.");
            } catch (IOException e) {
                Constants.printError("Loading failed: " + e.getMessage());
                break;
            } catch (ClassNotFoundException | ClassCastException e) {
                Constants.printError("Invalid file format.");
                break;
            }
        }
    }
}
