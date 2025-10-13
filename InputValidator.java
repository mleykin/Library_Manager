// Типы проверяемых данных
enum Attributes {genre, date, extension, attribute}

// Класс проверки корректности пользовательского ввода
public class InputValidator implements Constants {

    // Метод validate проверяет строку input в зависимости от атрибута
    public boolean validate(String input, Attributes attribute) {

        // Проверка даты
        if (attribute == Attributes.date) {
            if (input == null || input.trim().isEmpty()) {
                Constants.printError(ERROR_INVALID_DATE);
                return false;
            }
            try {
                int date = Integer.parseInt(input.trim());
                if (date >= MIN_DATE && date <= MAX_DATE) return true;
                else {
                    Constants.printError(ERROR_INVALID_DATE);
                    return false;
                }
            } catch (NumberFormatException e) {
                Constants.printError(ERROR_INVALID_DATE);
                return false;
            }
        }

        // Проверка жанра
        else if (attribute == Attributes.genre) {
            try {
                Genres genre = Genres.valueOf(input.trim().toLowerCase());
                return true;
            } catch (IllegalArgumentException e) {
                Constants.printError(ERROR_INVALID_GENRE);
                return false;
            }
        }

        // Проверка расширения файла
        else if (attribute == Attributes.extension) {
            if (input.equals(TERMINATE_SYMBOL) || input.toLowerCase().endsWith(FILE_EXTENSION)) return true;
            else {
                Constants.printError(ERROR_ILLEGAL_EXTENSION);
                return false;
            }
        }

        // Проверка названия атрибута
        else if (attribute == Attributes.attribute) {
            if (input.equalsIgnoreCase("name") || input.equalsIgnoreCase("author") ||
                    input.equalsIgnoreCase("genre") || input.equalsIgnoreCase("date") ||
                    input.equalsIgnoreCase("writing date")) return true;
            else {
                Constants.printError(ERROR_WRONG_PARAMETER);
                return false;
            }
        }

        return false;
    }
}
