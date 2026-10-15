
package simple_password_utility;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PasswordUtilityTest {
    @Test
    void hashPasswordReturnsEncodedHash() {
        char[] password = "TestPassword123!".toCharArray();

        String hash = PasswordUtility.hashPassword(password);

        assertNotNull(hash);
        assertFalse(hash.isEmpty());
        assertFalse(hash.equals("TestPassword123!"));
    }

    @Test
    void correctPasswordVerifiesSuccessfully() {
        String hash = PasswordUtility.hashPassword(
            "TestPassword123!".toCharArray()
        );

        boolean result = PasswordUtility.verifyPassword(
            "TestPassword123!".toCharArray(),
            hash
        );

        assertTrue(result);
    }

    @Test
    void incorrectPasswordFailsVerification() {
        String hash = PasswordUtility.hashPassword(
            "TestPassword123!".toCharArray()
        );

        boolean result = PasswordUtility.verifyPassword(
            "DifferentPassword456!".toCharArray(),
            hash
        );

        assertFalse(result);
    }

    @Test
    void hashingWipesPasswordArray() {
        char[] password = "TestPassword123!".toCharArray();

        PasswordUtility.hashPassword(password);

        assertArrayEquals(new char[password.length], password);
    }

    @Test
    void verificationWipesPasswordArray() {
        String hash = PasswordUtility.hashPassword(
            "TestPassword123!".toCharArray()
        );
        char[] password = "TestPassword123!".toCharArray();

        PasswordUtility.verifyPassword(password, hash);

        assertArrayEquals(new char[password.length], password);
    }

    @Test
    void hashingRejectsNullPassword() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PasswordUtility.hashPassword(null)
        );
    }

    @Test
    void verificationRejectsNullPassword() {
        assertThrows(
            IllegalArgumentException.class,
            () -> PasswordUtility.verifyPassword(null, "some-hash")
        );
    }

    @Test
    void verificationRejectsNullHashAndWipesPassword() {
        char[] password = "TestPassword123!".toCharArray();

        assertThrows(
            IllegalArgumentException.class,
            () -> PasswordUtility.verifyPassword(password, null)
        );

        assertArrayEquals(new char[password.length], password);
    }
}
