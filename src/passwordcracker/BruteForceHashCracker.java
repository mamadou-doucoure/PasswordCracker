package passwordcracker;

public class BruteForceHashCracker implements HashCracker {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int MAX_LENGTH = 4;

    private long attempts;

    @Override
    public String crack(String hash) {
        attempts = 0;
        for (int length = 1; length <= MAX_LENGTH; length++) {
            String found = generate(hash, new char[length], 0);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private String generate(String hash, char[] buffer, int index) {
        if (index == buffer.length) {
            String candidate = new String(buffer);
            attempts++;
            return MD5Util.hash(candidate).equalsIgnoreCase(hash) ? candidate : null;
        }

        for (int i = 0; i < ALPHABET.length(); i++) {
            buffer[index] = ALPHABET.charAt(i);
            String found = generate(hash, buffer, index + 1);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public long getAttempts() {
        return attempts;
    }
}
