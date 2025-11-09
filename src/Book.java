
public class Book {
    private String name, author;
    private Genres genre;
    private int date;

    public Book() {
        this(null, null, Genres.unknown, -1);
    }

//    public Book(String name, String author){
//        this(name, author, Genres.none, 0);
//    }
//
    public Book(String name, String author, Genres genre){
        this(name, author, genre, -1);
    }
//
//    public Book(String name, String author, int year){
//        this(name, author, Genres.none, year);
//    }

    public Book(String name, String author, Genres genre, int date){
        this.name=name;
        this.author=author;
        this.genre=genre;
        this.date=date;
    }

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

    //лучше было переопределить метод toString()    
    public void info(){
        System.out.println("Name: " + name + "\nAuthor: " + author +
                "\nGenre: " + genre);
        if (date != -1) System.out.println("Writing date: " + date + "\n");
        else System.out.println("Writing date: unknown\n");
    }

    //лучше было перелпределить метод equals()
    public boolean isEquals(Book book){
        if (book.getName().equals(name) &&
                book.getAuthor().equals(author) &&
                book.getGenre().equals(genre.name()) &&
                book.getDate() == date) return true;
        return false;
    }
}
