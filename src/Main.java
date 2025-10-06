//Консольное меню:
// добавить, вывести список, найти, поиск книги по атрибутам
// сохранить в файл, загрузить из файла, редактировать книгу

import java.io.*;
import java.util.*;

enum Genres {unknown, tragedy, comedy, novel, fiction, fantasy,
    business, politics, history, mystery, romance,
    horror, religious, health, autobiography}

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("It is a library manager.\n" +
                "You can create, edit, search, save and upload books.\n" +
                "To terminate press # when you're outside some process.");

        LinkedList<Book> collection = new LinkedList<Book>();
        LinkedList<Book> founded = new LinkedList<Book>();
        Manager manager = new Manager(scan);
        String command = new String();

        while(!command.equals("#")) {
            command = scan.nextLine();
            founded.clear();
            switch (command){
                case "help": case "Help": case "?":
                {
                    manager.help();
                    break;
                }
                case "create": case "Create":
                {
                    System.out.println("Creating new book");
                    Book new_book = manager.create();
                    if (manager.find_Duplicates(collection, new_book)) System.out.println("ERROR! This book is already exists!");
                    else collection.add(new_book);
                    break;
                }
                case "list": case "List":
                {
                    manager.list(collection);
                    break;
                }
                case "find": case "Find":
                {
                    manager.find(collection, founded);
                    break;
                }
                case "edit": case "Edit": {
                    Book edited_book = manager.find_Book(collection);
                    if (edited_book!=null) manager.edit(edited_book, collection);
                    break;
                }
                case "save": case "Save":{
                    manager.saveToFile(collection);
                    break;
                }
                case "upload": case "Upload":{
                    manager.loadFromFile(collection);
                    break;
                }
                default: System.out.println("Unknown command");
            }
        }
    }
}