package de.zazzam.articles.article;

import de.zazzam.articles.price.Price;
import de.zazzam.articles.unit.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Currency;
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
        Currency eur = Currency.getInstance("EUR");
        Currency usd = Currency.getInstance("USD");
        return Stream.of(
            Arguments.of("10001", "12345", "A beautiful thing", 5, UnitOfMeasure.Keg, new Price(1999L, eur)),
            Arguments.of("10002", "7753", "Sparkly Water", 6, UnitOfMeasure.Box, new Price(7599L, eur)),
            Arguments.of("55347", "78963", "Juicy Juice", 99, UnitOfMeasure.Container, new Price(12750L, usd)),
            Arguments.of("33445", "11223", "Golden Honey", 12, UnitOfMeasure.Bottle, new Price(899L, usd)),
            Arguments.of("77889", "66778", "Fresh Milk", 10, UnitOfMeasure.Case, new Price(1599L, eur)),
            Arguments.of("99001", "55644", "Organic Tea", 20, UnitOfMeasure.Tray, new Price(3499L, usd)),
            Arguments.of("44332", "99887", "Hot Coffee", 15, UnitOfMeasure.Dispenser, new Price(4599L, usd)),
            Arguments.of("88776", "22334", "Cold Brew", 8, UnitOfMeasure.BagInBox, new Price(2999L, eur)),
            Arguments.of("99887", "66789", "Choco Drink", 7, UnitOfMeasure.Item, new Price(1899L, usd)),
            Arguments.of("66554", "33445", "Protein Shake", 25, UnitOfMeasure.Keg, new Price(7599L, eur)),
            Arguments.of("55443", "22331", "Healthy Smoothie", 50, UnitOfMeasure.Container, new Price(12999L, eur)),
            Arguments.of("11223", "55444", "Ginger Ale", 30, UnitOfMeasure.Box, new Price(4999L, usd)),
            Arguments.of("77665", "99887", "Tonic Water", 14, UnitOfMeasure.Tray, new Price(3799L, eur)),
            Arguments.of("66543", "88776", "Energy Drink", 22, UnitOfMeasure.Dispenser, new Price(6899L, usd)),
            Arguments.of("99876", "22331", "Lemonade", 18, UnitOfMeasure.Bottle, new Price(2599L, usd)),
            Arguments.of("33221", "55677", "Iced Tea", 11, UnitOfMeasure.Case, new Price(3199L, eur))
        );
    }

    private static Stream<Arguments> provideArticlesWithEmptyArticleId() {
        return trimmableWhitespaceChars.stream()
            .flatMap(whitespaceChar -> replaceArg(provideValidArticleArguments(), 0, whitespaceChar));
    }

    private static Stream<Arguments> provideArticlesWithEmptyPartnerId() {
        return trimmableWhitespaceChars.stream()
            .flatMap(whitespaceChar -> replaceArg(provideValidArticleArguments(), 1, whitespaceChar));
    }

    private static Stream<Arguments> replaceArg(Stream<Arguments> arguments, int index, Object replacement) {
        return arguments.map(Arguments::get)
            .peek(args -> args[index] = replacement)
            .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("provideValidArticleArguments")
    void successfulInitialization(String articleId, String partnerId, String name, Integer content, UnitOfMeasure unit, Price price) {
        Article article = new Article(articleId, partnerId, name, content, unit, price);
        Assertions.assertEquals(articleId, article.articleId());
        Assertions.assertEquals(partnerId, article.partnerId());
        Assertions.assertEquals(content, article.content());
        Assertions.assertEquals(unit, article.unit());
        Assertions.assertEquals(price.amount(), article.price().amount());
        Assertions.assertEquals(price.currency(), article.price().currency());
    }

    @ParameterizedTest
    @MethodSource("provideValidArticleArguments")
    void missingArticleIdFails(String articleId, String partnerId, String name, Integer content, UnitOfMeasure unit, Price price) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Article(null, partnerId, name, content, unit, price));
    }

    @ParameterizedTest
    @MethodSource("provideArticlesWithEmptyArticleId")
    void emptyArticleIdFails(Character whitespaceChar, String partnerId, String name, Integer content, UnitOfMeasure unit, Price price) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Article(whitespaceChar.toString(), partnerId, name, content, unit, price));
    }

    @ParameterizedTest
    @MethodSource("provideValidArticleArguments")
    void missingPartnerIdFails(String articleId, String partnerId, String name, Integer content, UnitOfMeasure unit, Price price) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Article(articleId, null, name, content, unit, price));
    }

    @ParameterizedTest
    @MethodSource("provideArticlesWithEmptyPartnerId")
    void emptyPartnerIdFails(String articleId, Character whitespaceChar, String name, Integer content, UnitOfMeasure unit, Price price) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Article(articleId, whitespaceChar.toString(), name, content, unit, price));
    }

    @ParameterizedTest
    @MethodSource("provideValidArticleArguments")
    void missingContentFails(String articleId, String partnerId, String name, Integer content, UnitOfMeasure unit, Price price) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Article(articleId, partnerId, name, null, unit, price));
    }

    @ParameterizedTest
    @MethodSource("provideValidArticleArguments")
    void negativeContentFails(String articleId, String partnerId, String name, Integer content, UnitOfMeasure unit, Price price) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Article(articleId, partnerId, name, Math.abs(content) * -1, unit, price));
    }

    @ParameterizedTest
    @MethodSource("provideValidArticleArguments")
    void missingUnitFails(String articleId, String partnerId, String name, Integer content, UnitOfMeasure unit, Price price) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Article(articleId, partnerId, name, content, null, price));
    }

    @ParameterizedTest
    @MethodSource("provideValidArticleArguments")
    void missingPriceFails(String articleId, String partnerId, String name, Integer content, UnitOfMeasure unit, Price price) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Article(articleId, partnerId, name, content, unit, null));
    }

}
