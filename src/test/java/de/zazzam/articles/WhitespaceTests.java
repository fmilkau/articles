package de.zazzam.articles;

import com.google.common.base.CharMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class WhitespaceTests {

    // see https://en.wikipedia.org/wiki/Figure_space
    private static final Character figureSpace = '\u2007';

    // TODO - add all https://util.unicode.org/UnicodeJsps/list-unicodeset.jsp?a=%5Cp%7Bwhitespace%7D
    private static final List<Character> trimmableWhitespaceChars = Arrays.asList(
        ' ',     // Space (U+0020)
        '\t',    // Tab (U+0009)
        '\n',    // Line Feed (U+000A)
        '\r',    // Carriage Return (U+000D)
        '\f',    // Form Feed (U+000C)
        '\u000B' // Vertical Tab
    );

    private static final List<Character> nonTrimmableWhitespaceChars = Arrays.asList(
        '\u1680', // Ogham Space Mark
        '\u2000', // En Quad
        '\u2001', // Em Quad
        '\u2002', // En Space
        '\u2003', // Em Space
        '\u2004', // Three-Per-Em Space
        '\u2005', // Four-Per-Em Space
        '\u2006', // Six-Per-Em Space
        '\u2008', // Punctuation Space
        '\u2009', // Thin Space
        '\u200A', // Hair Space
        '\u2028', // Line Separator
        '\u2029', // Paragraph Separator
        '\u205F', // Medium Mathematical Space
        '\u3000'  // Ideographic Space
    );

    private static Stream<Arguments> provideTrimmableWhitespaceChars() {
        return trimmableWhitespaceChars.stream().map(Arguments::of);
    }

    private static Stream<Arguments> provideNonTrimmableWhitespaceChars() {
        return nonTrimmableWhitespaceChars.stream().map(Arguments::of);
    }

    private static Stream<Arguments> provideAllWhitespaceChars() {
        return Stream.of(
            provideTrimmableWhitespaceChars(),
            provideNonTrimmableWhitespaceChars(),
            Stream.of(Arguments.of(figureSpace))
        ).flatMap(i -> i);
    }

    @ParameterizedTest
    @MethodSource("provideTrimmableWhitespaceChars")
    public void isTrimmable(Character trimmableWhitespace) {
        Assertions.assertTrue(Character.isWhitespace(trimmableWhitespace));
        Assertions.assertTrue(trimmableWhitespace.toString().trim().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideNonTrimmableWhitespaceChars")
    public void isNonTrimmable(Character nonTrimmableWhitespace) {
        Assertions.assertTrue(Character.isWhitespace(nonTrimmableWhitespace));
        Assertions.assertFalse(nonTrimmableWhitespace.toString().isEmpty());
    }

    @Test
    public void figureSpaceIsNotWhitespace() {
        // for some reason figure space is not a whitespace (and not trimmable)
        Assertions.assertFalse(Character.isWhitespace(figureSpace));
        Assertions.assertFalse(figureSpace.toString().trim().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideAllWhitespaceChars")
    public void guavaTrimsAllWhitespace(Character whitespace) {
        // TODO - separators (file, group, record, unit) are not matched as whitespace
        CharMatcher.
        Assertions.assertTrue(CharMatcher.whitespace().matches(whitespace));
        Assertions.assertTrue(CharMatcher.whitespace().trimFrom(whitespace.toString()).isEmpty());
    }

}
