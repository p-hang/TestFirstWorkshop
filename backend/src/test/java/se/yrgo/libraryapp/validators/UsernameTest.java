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
    }

    @Test
    void incorrectNullName() {
        assertFalse(Username.validate(""));
    }


    @ParameterizedTest
    @ValueSource(strings={
            "name with space",
            "ääää",
            "£¤'name"
    })
    void testInCorrectNaWithOtherCharacters(String username) {
        assertThat(Username.validate(username)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings= {
            "a", "ab", "abc"
    })
    void TestIncorrectLengthName(String username) {
        assertThat(Username.validate(username)).isFalse();
    }

    @ParameterizedTest
    @EmptySource
    void TestIncorrectEmptyName(String username) {
        assertThat(Username.validate(username)).isFalse();
    }

    @Test
    void testUsernameLongerThan100000333Characters() {
        String username = "a".repeat(100000333);

        assertThat(Username.validate(username)).isTrue();
    }

}
