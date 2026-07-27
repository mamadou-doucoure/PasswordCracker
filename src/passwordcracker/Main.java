package passwordcracker;

public class Main {

    public static void main(String[] args) {
        String method = null;
        String hash = null;
        String dictionaryPath = "resources/dictionary.txt";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-m":
                    method = args[++i];
                    break;
                case "-h":
                    hash = args[++i];
                    break;
                case "-d":
                    dictionaryPath = args[++i];
                    break;
                default:
                    System.err.println("Argument inconnu : " + args[i]);
            }
        }

        if (method == null || hash == null) {
            System.out.println("Usage: passwordCracker -m BRUTE|DICO -h <hashMD5> [-d dictionnaire]");
            return;
        }

        HashCracker cracker;
        try {
            cracker = HashCrackerFactory.create(method, dictionaryPath);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        long start = System.currentTimeMillis();
        String password = cracker.crack(hash);
        long elapsed = System.currentTimeMillis() - start;

        if (password != null) {
            System.out.println("Password found: " + password);
        } else {
            System.out.println("Password not found");
        }

        System.out.println("Temps d'execution : " + elapsed + " ms");
        printAttempts(cracker);
    }

    private static void printAttempts(HashCracker cracker) {
        if (cracker instanceof DictionaryHashCracker) {
            System.out.println("Tentatives : " + ((DictionaryHashCracker) cracker).getAttempts());
        } else if (cracker instanceof BruteForceHashCracker) {
            System.out.println("Tentatives : " + ((BruteForceHashCracker) cracker).getAttempts());
        }
    }
}
