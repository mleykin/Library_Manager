import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
enum Atributes {genre, date}

public class Manager {
    final Scanner scan;
    final InputValidator validator = new InputValidator();

    public Manager() {
        this(null);
        System.out.println("ERROR: Manager can't be used without scanner!");
    }

    public Manager(Scanner scanner){
        scan = scanner;
    }

    public void help(){
        System.out.println("Commands:\nCreate new book: create\n" +
                "Check all books in collection: list\n" +
                "Find books with attribute: find\n" +
                "Edit attribute in one book: edit\n" +
                "Save collection tp file: save\n" +
                "Upload collection from file: upload\n" +
                "After choosing the command follow instructions");
    }

    //может ли юзер передумать создавать книгу и выйти по ходу процесса?
    public Book create(){
        System.out.println("Insert name:");
        String name = new String();
        while (true) {
            name=scan.nextLine();
            name = name.trim();
            if (!name.isEmpty()) break;
            else System.out.println("ERROR: The space is invalid name! \nInsert again: ");
        }

        System.out.println("Insert author:");
        String author = new String();
        while (true) {
            author=scan.nextLine();
            author = author.trim();
            if (!author.isEmpty()) break;
            else System.out.println("ERROR: The space is invalid author! \nInsert again: ");
        }

        System.out.println("Insert genre:");
        Genres genre = null;
        String input = new String();
        while (true) {
            input = scan.nextLine();
            //input = input.trim();
            if (validator.validate(input, Atributes.genre)) {
                genre = Genres.valueOf(input);
                break;
            }
        }

        System.out.println("Insert writing date:");
        int date = -1;
        input = "";
        while (true) {
            input = scan.nextLine();
            //input = input.trim();
            if (input.equals("Unknown") || input.equals("unknown")) break;
            else if (validator.validate(input, Atributes.date)) {
                date = Integer.parseInt(input);
                break;
            }
        }

        Book new_book = new Book(name, author, genre, date);
        return new_book;
    }

    public void list(LinkedList<Book> books) {
        if (books.isEmpty()) System.out.println("ERROR: The list is empty!");
        else {
            System.out.println("Books in collection:");
            for (Book book : books) book.info();
        }
    }

    public boolean find_Duplicates(LinkedList<Book> books, Book cur_book){
        if (books.isEmpty()) return false;
        for (Book book : books) {
            if (cur_book.isEquals(book)) return true;
        }
        return false;
    }

    public void find(LinkedList<Book> books, LinkedList<Book> founded) {
        if (books.isEmpty()) {
            System.out.println("ERROR: The collection is empty!");
            return;
        } else {
            System.out.println("Choose attribute for finding:");
            String attribute = scan.nextLine();
            switch (attribute) {
                case "name":
                case "Name": {
                    System.out.println("Insert name:");
                    String name = scan.nextLine();
                    for (Book book : books) {
                        if (book.getName().equals(name)) founded.add(book);
                    }
                    break;
                }

                case "author":
                case "Author": {
                    System.out.println("Insert author:");
                    String author = scan.nextLine();
                    for (Book book : books) {
                        if (book.getAuthor().equals(author)) founded.add(book);
                    }
                    break;
                }

                case "genre":
                case "Genre": {
                    System.out.println("Insert genre:");
                    String input = scan.nextLine();
                    if (validator.validate(input, Atributes.genre)) {
                        Genres genre = Genres.valueOf(input);
                        for (Book book : books) {
                            if (book.getGenre().equals(genre.name())) { // используй enum напрямую
                                founded.add(book);
                            }
                        }
                    }
                    break;
                }

                case "writing date":
                case "Writing date":
                case "date":
                case "Date": {
                    System.out.println("Insert writing date:");
                    String input = scan.nextLine();
                    if (input.equals("Unknown") || input.equals("unknown") || validator.validate(input, Atributes.date)) {
                        int date;
                        if (input.equals("Unknown") || input.equals("unknown")) date = -1;
                        else date = Integer.parseInt(input);
                        for (Book book : books) {
                            if (book.getDate() == date) founded.add(book);
                        }
                    }
                    break;
                }
                default:
                    System.out.println("ERROR: Wrong attribute!");
            }
            if (!founded.isEmpty()) for (Book cur_book : founded) cur_book.info();
            else System.out.println("No books are founded!");
        }
    }

    public Book find_Book(LinkedList<Book> books) {
        if (!books.isEmpty()) {
            LinkedList<Book> additional = new LinkedList<Book>();
            LinkedList<Book> founded = new LinkedList<Book>();
            Book book = new Book();
            String answer;
            do {
                System.out.println("Finding one book in collection:");
                find(books, founded);
                while (founded.size() > 1) {
                    System.out.println("Founded: ");
                    for (Book cur_book : founded) cur_book.info();
                    System.out.println("NOTIFY: Can't recognize the book! \nChoose one more attribute for identifying!");
                    find(founded, additional);
                    founded.clear();
                    founded.addAll(additional);
                    additional.clear();
                }
                if (founded.isEmpty()) {
                    System.out.println("No books are founded!");
                    break;
                }
                book = founded.getFirst();
                book.info();
                System.out.println("Did you find this element? (print y/n)");
                answer = scan.nextLine();
                if (answer.equals("y")) {
                    System.out.println("Book successfully recognized!");
                    return book;
                }
            } while (answer.equals("n"));
        } else {
            System.out.println("ERROR: The collection is empty!");
        }
        return null;
    }

    public void edit(Book book, LinkedList<Book> allBooks){
        Book tempBook = new Book(book.getName(), book.getAuthor(),
                Genres.valueOf(book.getGenre()), book.getDate());
        System.out.println("Choose attribute for editing:");
        String param = scan.nextLine();
        switch (param) {
            case "name": case "Name": {
                System.out.println("Insert new name:");
                String input = scan.nextLine();
                //здесь валидация на пустое  имя не делается
                tempBook.setName(input);
                break;
            }
            case "author": case "Author": {
                System.out.println("Insert new author:");
                String input = scan.nextLine();
                tempBook.setAuthor(input);
                break;
            }
            case "genre": case "Genre": {
                System.out.println("Insert new genre:");
                String input;
                Genres genre = null;
                while (true) {
                    input = scan.nextLine();
                    input = input.trim();
                    if (validator.validate(input, Atributes.genre)) {
                        genre = Genres.valueOf(input);
                        break;
                    }
                }
                tempBook.setGenre(genre);
                break;
            }
            case "date": case "Date":
            case "writing date": case "Writing date": {
                System.out.println("Insert new writing date:");
                int date = -1;
                String input;
                while (true) {
                    input = scan.nextLine();
                    input = input.trim();
                    if (input.equalsIgnoreCase("Unknown")) {
                        date = -1;
                        break;
                    } else if (validator.validate(input, Atributes.date)) {
                        date = Integer.parseInt(input);
                        break;
                    }
                }
                tempBook.setDate(date);
                break;
            }
            default: {
                System.out.println("Unknown parameter!");
                //return false;
            }
        }

        for (Book existingBook : allBooks) {
            if (existingBook == book) continue;
            if (tempBook.isEquals(existingBook)) {
                System.out.println("ERROR: This change would create a duplicate book!");
                return;
            }
        }

        book.setName(tempBook.getName());
        book.setAuthor(tempBook.getAuthor());
        book.setGenre(Genres.valueOf(tempBook.getGenre()));
        book.setDate(tempBook.getDate());
        System.out.println("Book edited successfully!");
    }

    // Убери throws Exception
    public void saveToFile(LinkedList<Book> books) {
        try {
            System.out.println("Insert filename (ex. 'lib.txt'):");
            String filename = scan.nextLine();

            if (!filename.endsWith(".txt")) {
                System.out.println("ERROR: File must have .txt extension!");
                return;
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                for (Book book : books) {
                    writer.print(book.getName() + "|" + book.getAuthor() + "|" +
                            book.getGenre() + "|");
                    if (book.getDate()==-1) writer.println("unknown");
                    else writer.println(book.getDate());
                }
                System.out.println("Books saved successfully!");
            }
        } catch (IOException e) {
            System.out.println("ERROR: error saving file: " + e.getMessage());
        }
    }

    public void loadFromFile(LinkedList<Book> books) {
        System.out.println("Insert filename (ex. 'lib.txt'):");
        String filename = scan.nextLine();

        if (!filename.endsWith(".txt")) {
            throw new IllegalArgumentException();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            books.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String name = parts[0];
                    String author = parts[1];
                    Genres genre = Genres.valueOf(parts[2]);
                    int date = -1;
                    if (!parts[3].equals("unknown")) date = Integer.parseInt(parts[3]);
                    books.add(new Book(name, author, genre, date));
                }
            }
            System.out.println("Books loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found!");
        } catch (IOException e) {
            System.out.println("ERROR: error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e){
            System.out.println("ERROR: Illegal extension name! Use '.txt' only");
        }
    }
}
