package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class UtilsTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "a@¤%&b&()c",
            "abc@123",
            "ABC"
    })
    void testRemoveNonAlphabetic(String str) {
        assertThat(Utils.onlyLettersAndWhitespace(str)).isEqualTo("abc");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "a b c@123",
            "A B C",
            "a b c"
    })
    void testOnlyLettersAndWhitespace(String str) {
        assertThat(Utils.onlyLettersAndWhitespace(str)).isEqualTo("a b c");
    }

    @ParameterizedTest
    @EmptySource
    void testOnlyLettersAndWhitespaceEmpty(String str) {
        assertThat(Utils.onlyLettersAndWhitespace(str)).isEqualTo("");
    }

    @Test
    void testNullInput() {
        assertThatNullPointerException()
                .isThrownBy(() -> Utils.onlyLettersAndWhitespace(null));
    }
}
