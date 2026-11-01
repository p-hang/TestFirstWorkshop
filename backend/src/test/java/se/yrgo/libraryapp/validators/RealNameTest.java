package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RealNameTest {

    @Test
    void testUsernameLongerThan100Characters() {
        String realName = "a".repeat(1020);

        assertThat(Username.validate(realName)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "balderdash",
            "blimey",
            "bullspit",
            "damn",
            "darn",
            "drat",
            "frack",
            "frick",
            "heck",
            "shiet"
    })
    void testBadWordsAreLoaded(String badWord) {
        assertThat(RealName.validate("John " + badWord)).isFalse();
    }

    @Test
    void testBadWordAtBeginningOfName() {
        assertThat(RealName.validate("damn John")).isFalse();
    }
}
