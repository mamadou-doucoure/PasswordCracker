package passwordcracker;

public class HashCrackerFactory {

    private static final String DEFAULT_DICTIONARY = "resources/dictionary.txt";

    private HashCrackerFactory() {
    }

    public static HashCracker create(String method) {
        return create(method, DEFAULT_DICTIONARY);
    }

    public static HashCracker create(String method, String dictionaryPath) {
        if (method == null) {
            throw new IllegalArgumentException("La methode de cassage est obligatoire (BRUTE ou DICO)");
        }

        switch (method.toUpperCase()) {
            case "DICO":
                return new DictionaryHashCracker(dictionaryPath);
            case "BRUTE":
                return new BruteForceHashCracker();
            default:
                throw new IllegalArgumentException("Methode inconnue : " + method + " (attendu BRUTE ou DICO)");
        }
    }
}
