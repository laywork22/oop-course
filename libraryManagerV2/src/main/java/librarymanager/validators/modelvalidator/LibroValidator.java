package librarymanager.validators.modelvalidator;

public class LibroValidator {
    public static boolean isCopieTotaliValido(int copieTotali) {
        return copieTotali > 0;
    }

    public static boolean isISBNValido(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            return false;
        }

        String isbnRegex = "^(?:(?:\\d[- ]?){13}|(?:\\d[- ]?){9}[\\dXx])$" ;

        return isbn.trim().matches(isbnRegex);
    }

    public static boolean isLibroValido(String isbn, int copieTotali) {
        return isCopieTotaliValido(copieTotali) && isISBNValido(isbn);
    }
}
