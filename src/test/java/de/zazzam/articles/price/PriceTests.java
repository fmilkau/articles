package de.zazzam.articles.price;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Currency;
import java.util.stream.Stream;

public class PriceTests {

    private static Stream<Arguments> provideValidPrices() {
        Currency eur = Currency.getInstance("EUR");
        Currency usd = Currency.getInstance("USD");
        return Stream.of(
                Arguments.of(1999, eur),
                Arguments.of(99, eur),
                Arguments.of(32999, eur),
                Arguments.of(2500, usd),
                Arguments.of(500, eur),
                Arguments.of(999, usd),
                Arguments.of(1350, usd),

                // negative prices (... are allowed)
                Arguments.of(-250, eur),
                Arguments.of(-3599, usd),
                Arguments.of(-420000, eur)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidPrices")
    public void testValidPrice(long amount, Currency currency) {
        Price price = new Price(amount, currency);
        Assertions.assertEquals(amount, price.amount());
        Assertions.assertEquals(currency, price.currency());
    }

    @ParameterizedTest
    @MethodSource("provideValidPrices")
    void missingAmountFails(long amount, Currency currency) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Price(null, currency));
    }

    @ParameterizedTest
    @MethodSource("provideValidPrices")
    void missingCurrencyFails(long amount, Currency currency) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Price(amount, null));
    }

}