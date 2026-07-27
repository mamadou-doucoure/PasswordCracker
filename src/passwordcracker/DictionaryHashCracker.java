package passwordcracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DictionaryHashCracker implements HashCracker {

    private final String dictionaryPath;
    private long attempts;

    public DictionaryHashCracker(String dictionaryPath) {
        this.dictionaryPath = dictionaryPath;
    }

    @Override
    public String crack(String hash) {
        attempts = 0;
        List<String> words;
        try {
            words = Files.readAllLines(Paths.get(dictionaryPath));
        } catch (IOException e) {
            System.err.println("Impossible de lire le dictionnaire '" + dictionaryPath + "' : " + e.getMessage());
            return null;
        }

        for (String word : words) {
            String candidate = word.trim();
            if (candidate.isEmpty()) {
                continue;
            }
            attempts++;
            if (MD5Util.hash(candidate).equalsIgnoreCase(hash)) {
                return candidate;
            }
        }
        return null;
    }

    public long getAttempts() {
        return attempts;
    }
}
