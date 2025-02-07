package de.zazzam.articles.article;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PriceTests {

    private static Stream<Arguments> provideValidPrices() {
        return Stream.of(
                Arguments.of(1999, Currency.EUR),
                Arguments.of(99, Currency.EUR),
                Arguments.of(32999, Currency.EUR),
                Arguments.of(2500, Currency.USD),
                Arguments.of(500, Currency.EUR),
                Arguments.of(999, Currency.USD),
                Arguments.of(1350, Currency.USD),

                // negative prices (... are allowed)
                Arguments.of(-250, Currency.EUR),
                Arguments.of(-3599, Currency.USD),
                Arguments.of(-420000, Currency.EUR)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidPrices")
    public void testValidPrice(int amount, Currency currency) {
        Price price = new Price(amount, currency);
        Assertions.assertEquals(amount, price.amount());
        Assertions.assertEquals(currency, price.currency());
    }

    @ParameterizedTest
    @MethodSource("provideValidPrices")
    void missingAmountFails(int amount, Currency currency) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Price(null, currency));
    }

    @ParameterizedTest
    @MethodSource("provideValidPrices")
    void missingCurrencyFails(int amount, Currency currency) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Price(amount, null));
    }

}