package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

public class UsernameTest {
    @Test
    void correctUsername() {
        assertTrue(Username.validate("bosse"));
        assertTrue(Username.validate("acb1234"));
    }

    @Test
    void incorrectUsername() {
        assertFalse(Username.validate("name with space"));
        assertFalse(Username.validate("ååååå"));
        assertFalse(Username.validate("¤%a"));
        assertFalse(Username.validate("a12345"));
    }

    @Test
    void incorrectNullName() {
        assertFalse(Username.validate(""));
    }
}
