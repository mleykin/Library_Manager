//Консольное меню:
// добавить, вывести список, найти, поиск книги по атрибутам
// сохранить в файл, загрузить из файла, редактировать книгу

import java.io.*;
import java.util.*;

enum Genres {unknown, tragedy, comedy, novel, fiction, fantasy,
    business, politics, history, mystery, romance,
    horror, religious, health, autobiography}

enum Atributes {name, author, genre, date}


public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("It is a library manager.\n" +
                "Here you can add, redact, search and save books.\n" +
                "To terminate press # when you're outside some process.");

        LinkedList<Book> books = new LinkedList<Book>();
        LinkedList<Book> founded = new LinkedList<Book>();
        Manager manager = new Manager();
        String command = new String();

        while(!command.equals("#")) {
            command = scan.nextLine();
            founded.clear();
            switch (command){
                case "help": case "Help": case "?":
                {
                    System.out.println("For creating new book - add");
                    break;
                }
                case "add": case "Add":
                {
                    System.out.println("Creating new book");
                    Book new_book = manager.create();
                    if (manager.find_Duplicates(books, new_book)) System.out.println("ERROR! This book is already exists!");
                    else books.add(new_book);
                    break;
                }
                case "list": case "List":
                {
                    manager.list(books);
                    break;
                }
                case "find": case "Find":
                {
                    manager.find(books, founded);
                    break;
                }
                case "edit": case "Edit": {
                    Book edicted_book = manager.find_Book(books);
                    if (edicted_book!=null) manager.edit(edicted_book, books);
                    break;
                }
                case "save": case "Save":{
                    System.out.println("Insert filename (ex. 'lib.txt'):");
                    String filename = scan.nextLine();
                    manager.saveToFile(books, filename);
                    break;
                }
                case "upload": case "Upload":{
                    System.out.println("Insert filename (ex. 'lib.txt'):");
                    String filename = scan.nextLine();
                    manager.loadFromFile(books, filename);
                    break;
                }
                default: System.out.println("Unknown command");
            }
        }
    }
}