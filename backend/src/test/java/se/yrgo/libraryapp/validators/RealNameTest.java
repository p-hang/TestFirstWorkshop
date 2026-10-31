package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RealNameTest {

    @Test
    void testUsernameLongerThan100Characters() {
        String realName = "a".repeat(1020);

        assertThat(Username.validate(realName)).isFalse();
    }
}
