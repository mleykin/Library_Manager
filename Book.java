import java.io.Serializable;

// Жанры книг
enum Genres implements Serializable {
    unknown, tragedy, comedy, novel, fiction, fantasy,
    business, politics, history, mystery, romance,
    horror, religious, health, autobiography
}


public class Book implements Serializable, Constants {
    private static final long serialVersionUID = 1L;

    private String name, author;
    private Genres genre;
    private int date;

    // Конструктор по умолчанию
    public Book() {
        this("", "", Genres.unknown, UNKNOWN_NUMBER);
    }

    // Конструктор без даты
    public Book(String name, String author, Genres genre) {
        this(name, author, genre, UNKNOWN_NUMBER);
    }

    // Основной конструктор
    public Book(String name, String author, Genres genre, int date) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.date = date;
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre.name();
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    // Вывод информации о книге
    public void info() {
        System.out.println("Name: " + name + "\nAuthor: " + author +
                "\nGenre: " + genre);
        if (date != UNKNOWN_NUMBER)
            System.out.println("Writing date: " + date + "\n");
        else
            System.out.println("Writing date: unknown\n");
    }

    // Проверка на полное совпадение с другой книгой
    public boolean isEquals(Book book) {
        if (book == null) return false;

        return book.getName().equals(name) &&
                book.getAuthor().equals(author) &&
                book.getGenre().equals(genre.name()) &&
                book.getDate() == date;
    }
}
