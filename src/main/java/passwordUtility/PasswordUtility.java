package passwordUtility;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public final class PasswordUtility {

    // Tunable security parameters
    private static final int ITERATIONS = 3;
    private static final int MEMORY_KB  = 64 * 1024; // 64 MB
    private static final int PARALLELISM = 1;

    private PasswordUtility() {
    }

    /**
     * Hashes a password using Argon2id.
     *
     * @param password plaintext password
     * @return encoded Argon2 hash (includes salt + params)
     */
    public static String hashPassword(char[] password) {
        Argon2 argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id
        );

        try {
            return argon2.hash(
                ITERATIONS,
                MEMORY_KB,
                PARALLELISM,
                password
            );
        } finally {
            argon2.wipeArray(password);
        }
    }

    /**
     * Verifies a password against a stored Argon2 hash.
     *
     * @param password plaintext password
     * @param hash     stored hash
     * @return true    if valid
     */
    public static boolean verifyPassword(char[] password, String hash) {
        Argon2 argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id
        );

        try {
            return argon2.verify(hash, password);
        } finally {
            argon2.wipeArray(password);
        }
    }
}