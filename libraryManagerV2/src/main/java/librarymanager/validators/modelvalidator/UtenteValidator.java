package librarymanager.validators.modelvalidator;

public class UtenteValidator {
    public static boolean isEmailValida(String email) {
        if (email == null || email.isBlank()) return false;

        String emailRegex = "^[a-z]\\.[a-z]+[0-9]*@studenti\\.unisa\\.it$";

        return email.trim().matches(emailRegex);
    }

    public static boolean  isMatricolaValida(String matricola) {
        if (matricola == null || matricola.isBlank()) {
            return false;
        }

        String matricolaRegex = "^[0-9]{10}$";

        return matricola.trim().matches(matricolaRegex);
    }

    public static boolean isUtenteValido(String email, String matricola) {
        if (email == null || matricola == null) return false;

        return isEmailValida(email) && isMatricolaValida(matricola);
    }
}
