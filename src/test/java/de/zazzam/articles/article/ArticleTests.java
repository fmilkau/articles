package de.zazzam.articles.article;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class ArticleTests {

    private static final List<Character> trimmableWhitespaceChars = Arrays.asList(
        ' ',     // Space (U+0020)
        '\t',    // Tab (U+0009)
        '\n',    // Line Feed (U+000A)
        '\r',    // Carriage Return (U+000D)
        '\f',    // Form Feed (U+000C)
        '\u000B', // Vertical Tab
        '\u001C', // File Separator
        '\u001D', // Group Separator
        '\u001E', // Record Separator
        '\u001F' // Unit Separator
    );

    private static Stream<Arguments> provideValidArticleArguments() {
        return Stream.of(
            Arguments.of("10001", "12345", "A beautiful thing", 5, ArticleUnit.Keg, new Price(1999, Currency.EUR))
            // TODO - add more cases
        );
    }

    private static Stream<Arguments> provideArticlesWithEmptyArticleId() {
        Stream.Builder<Arguments> arguments = Stream.builder();
        for (Character c : trimmableWhitespaceChars) {
            // replace the first argument (articleId) with the whitespace character c (later used as articleId)
            provideValidArticleArguments()
                .map(Arguments::get)
                .map(args -> Arrays.copyOfRange(args, 1, args.length)) // truncate array (1 to end)
                .map(args -> Stream.concat(Stream.of(c), Arrays.stream(args)).toArray()) // prepend c
                .map(Arguments::of)
                .forEach(arguments::add);
        }
        return arguments.build();
    }

    private static Stream<Arguments> provideArticlesWithEmptyPartnerId() {
        Stream.Builder<Arguments> arguments = Stream.builder();
        for (Character c : trimmableWhitespaceChars) {
            // replace the first argument (articleId) with the whitespace character c (later used as articleId)
            provideValidArticleArguments()
                .map(Arguments::get)
                .map(args -> Arguments.of(args[0], c, args[2], args[3], args[4], args[5])) // replace 2nd arg (partnerId)
                .forEach(arguments::add);
        }
        return arguments.build();
    }

    @ParameterizedTest
    @MethodSource("provideValidArticleArguments")
    void successfulInitialization(String articleId, String partnerId, String name, Integer amount, ArticleUnit unit, Price price) {
        Article article = new Article(articleId, partnerId, name, amount, unit, price);
        Assertions.assertEquals(articleId, article.articleId());
        Assertions.assertEquals(partnerId, article.partnerId());
        Assertions.assertEquals(amount, article.amount());
        Assertions.assertEquals(unit, article.unit());
        Assertions.assertEquals(price.amount(), article.price().amount());
        Assertions.assertEquals(price.currency(), article.price().currency());
    }

    @ParameterizedTest
    @MethodSource("provideValidArticleArguments")
    void missingArticleIdFails(String articleId, String partnerId, String name, Integer amount, ArticleUnit unit, Price price) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
            () -> new Article(null, partnerId, name, amount, unit, price));
    }

    @ParameterizedTest
    @MethodSource("provideArticlesWithEmptyArticleId")
    void emptyArticleIdFails(Character whitespaceChar, String partnerId, String name, Integer amount, ArticleUnit unit, Price price) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
            () -> new Article(whitespaceChar.toString(), partnerId, name, amount, unit, price));
    }

    @ParameterizedTest
    @MethodSource("provideValidArticleArguments")
    void missingPartnerIdFails(String articleId, String partnerId, String name, Integer amount, ArticleUnit unit, Price price) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
            () -> new Article(articleId, null, name, amount, unit, price));
    }

    @ParameterizedTest
    @MethodSource("provideArticlesWithEmptyPartnerId")
    void emptyPartnerIdFails(String articleId, Character whitespaceChar, String name, Integer amount, ArticleUnit unit, Price price) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
            () -> new Article(articleId, whitespaceChar.toString(), name, amount, unit, price));
    }

}
