import java.util.Scanner;

public class InputValidator {
    public boolean validate(String input, Atributes atribute) {
        if (atribute == Atributes.date) {
            try {
                int date = Integer.parseInt(input);
                if (date>0 && date<2025) return true;
            } catch (NumberFormatException e) {
                System.out.println("ERROR! Invalid date! \nInsert again: ");
                return false;
            }
        }

        else if (atribute == Atributes.genre) {
            try {
                Genres genre = Genres.valueOf(input);
                return true;
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR! Invalid genre! \nInsert again:");
                return false;
            }
        }
        return false;
    }
}