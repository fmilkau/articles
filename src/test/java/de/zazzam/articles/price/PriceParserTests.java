package de.zazzam.articles.price;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;
import java.util.stream.Stream;

public class PriceParserTests {

    private static Stream<Arguments> provideGermanEuroSamples() {
        return Stream.of(
            Arguments.of("9,99", new Price(999L, Currency.getInstance("EUR"))),
            Arguments.of("1.234,56", new Price(123456L, Currency.getInstance("EUR"))),
            Arguments.of("0,99", new Price(99L, Currency.getInstance("EUR"))),
            Arguments.of("10,00", new Price(1000L, Currency.getInstance("EUR"))),
            Arguments.of("100,50", new Price(10050L, Currency.getInstance("EUR"))),
            Arguments.of("999,99", new Price(99999L, Currency.getInstance("EUR"))),
            Arguments.of("12.345,67", new Price(1234567L, Currency.getInstance("EUR"))),
            Arguments.of("1.000.000,00", new Price(100000000L, Currency.getInstance("EUR"))),
            Arguments.of("50", new Price(5000L, Currency.getInstance("EUR"))),
            Arguments.of("0", new Price(0L, Currency.getInstance("EUR")))
        );
    }

    private static Stream<Arguments> provideGermanUnidadDeFomentoSamples() {
        return Stream.of(
            Arguments.of("9,9999", new Price(99999L, Currency.getInstance("CLF"))),
            Arguments.of("1,2345", new Price(12345L, Currency.getInstance("CLF"))),
            Arguments.of("0,1234", new Price(1234L, Currency.getInstance("CLF"))),
            Arguments.of("10,0000", new Price(100000L, Currency.getInstance("CLF"))),
            Arguments.of("100,5000", new Price(1005000L, Currency.getInstance("CLF"))),
            Arguments.of("999,9999", new Price(9999999L, Currency.getInstance("CLF"))),
            Arguments.of("12.345,6789", new Price(123456789L, Currency.getInstance("CLF"))),
            Arguments.of("1.000.000,0000", new Price(10000000000L, Currency.getInstance("CLF"))),
            Arguments.of("50,0000", new Price(500000L, Currency.getInstance("CLF"))),
            Arguments.of("0,0000", new Price(0L, Currency.getInstance("CLF")))
        );
    }

    @ParameterizedTest
    @MethodSource("provideGermanEuroSamples")
    void germanLocaleEuroTest(String priceString, Price expectedPrice) throws ParseException {
        PriceParser parser = new PriceParserImpl(Currency.getInstance("EUR"), NumberFormat.getInstance(Locale.GERMANY));
        Price price = parser.fromString(priceString);
        Assertions.assertEquals(expectedPrice.amount(), price.amount());
        Assertions.assertEquals(expectedPrice.currency(), price.currency());
    }

    @ParameterizedTest
    @MethodSource("provideGermanUnidadDeFomentoSamples")
    void germanLocaleUnidadDeFomentoTest(String priceString, Price expectedPrice) throws ParseException {
        PriceParser parser = new PriceParserImpl(Currency.getInstance("CLF"), NumberFormat.getInstance(Locale.GERMANY));
        Price price = parser.fromString(priceString);
        Assertions.assertEquals(expectedPrice.amount(), price.amount());
        Assertions.assertEquals(expectedPrice.currency(), price.currency());
    }

}
