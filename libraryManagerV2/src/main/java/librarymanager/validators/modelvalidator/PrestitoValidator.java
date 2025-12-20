package librarymanager.validators.modelvalidator;

public class PrestitoValidator {
    public static boolean isPrestitAttiviMinTre(int prestitiAttivi) {
        return prestitiAttivi < 3;
    }

    public static boolean isCopieDisponibiliNotZero(int copieDisponibili) {
        return copieDisponibili > 0;
    }

    public static boolean isPrestitoValido(int prestitiAttivi, int copieDisponibili) {
        return isPrestitAttiviMinTre(prestitiAttivi) && isCopieDisponibiliNotZero(copieDisponibili);
    }

}
