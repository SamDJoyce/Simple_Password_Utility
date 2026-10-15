package simple_password_utility;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 * A reusable password hashing and verification utility class.
 * Implemented in Java jdk-21.0.11
 *
 * @author Sam Joyce
 */
public final class PasswordUtility {

    // Tunable security parameters
    private static final int ITERATIONS = 3;
    private static final int MEMORY_KB  = 64 * 1024; // 64 MB
    private static final int PARALLELISM = 1;

    private PasswordUtility() {
    }

    /**
     * Hashes a password using Argon2id.
     * Wipes the provided char array after hashing
     *
     * @param password plaintext password
     * @return encoded Argon2 hash (includes salt + params)
     * @throws IllegalArgumentException if password is null
     */
    public static String hashPassword(char[] password) throws IllegalArgumentException {
        // Validate password is not null
        if (passwordIsNull(password)) {
        	throw new IllegalArgumentException("Password cannot be null");
        }
        // Create the hashing object
    	Argon2 argon2 = createArgon2();        
        // Hash the password and return it as a String
        try {
            return argon2.hash(
                ITERATIONS,
                MEMORY_KB,
                PARALLELISM,
                password
            );
        } finally {
        	// Erase the unhashed password from the provided array
        	java.util.Arrays.fill(password, '\0');
        }
    }

    /**
     * Verifies a password against a stored Argon2 hash.
     *
     * @param password plaintext password
     * @param hash     stored hash
     * @return true    if inputs match
     * @throws IllegalArgumentException if password or hash are null
     */
    public static boolean verifyPassword(char[] password, String hash) {
    	 // Validate password is not null
    	if (passwordIsNull(password)) {
        	throw new IllegalArgumentException("Password cannot be null");
        }

    	// Create the argon2 object
    	Argon2 argon2 = createArgon2();
    	// Compare stored hash and supplied password
        try {
        	// Validate hash is not null
            if (hashIsNull(hash)) {
            	throw new IllegalArgumentException("Hash cannot be null");
            }
            return argon2.verify(hash, password);
        } finally {
        	// Wipe the provided array
        	java.util.Arrays.fill(password, '\0');
        }
    }
    
    /**
     * @param password to be examined
     * @return true if the char[] is null or has no content
     */
    private static boolean passwordIsNull(char[] password) {
    	return password == null ||  password.length < 1;
    }
    /**
     * @param hash to be examined
     * @return true if the string is null or has no content
     */
    private static boolean hashIsNull(String hash) {
    	return hash == null ||  hash.isEmpty();
    }
    
    /**
     * @return an Argon2 object
     */
    private static Argon2 createArgon2() {
    	return Argon2Factory.create(
                Argon2Factory.Argon2Types.ARGON2id
            );        
    }
}