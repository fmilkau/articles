package de.zazzam.articles.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class GermanUnitOfMeasureMapperTests {

    private static Stream<Arguments> provideValidMappings() {
        return Stream.of(
            Arguments.of("Karton", UnitOfMeasure.Box),
            Arguments.of("Kasten", UnitOfMeasure.Case),
            Arguments.of("Tray", UnitOfMeasure.Tray),
            Arguments.of("Dispenser", UnitOfMeasure.Dispenser),
            Arguments.of("BIB", UnitOfMeasure.BagInBox),
            Arguments.of("Container", UnitOfMeasure.Container),
            Arguments.of("Fass", UnitOfMeasure.Keg),
            Arguments.of("Flasche", UnitOfMeasure.Bottle),
            Arguments.of("Stück", UnitOfMeasure.Item),

            // case-insensitive and trimmed input variations
            Arguments.of("karton", UnitOfMeasure.Box),
            Arguments.of("KASTEN", UnitOfMeasure.Case),
            Arguments.of("  tray  ", UnitOfMeasure.Tray),
            Arguments.of("dispenser", UnitOfMeasure.Dispenser),
            Arguments.of("bib", UnitOfMeasure.BagInBox),
            Arguments.of("container", UnitOfMeasure.Container),
            Arguments.of("fass", UnitOfMeasure.Keg),
            Arguments.of("flasche", UnitOfMeasure.Bottle),
            Arguments.of("stück", UnitOfMeasure.Item)
        );
    }

    static Stream<Arguments> provideInvalidMappings() {
        return Stream.of(
            Arguments.of("Boxen"),
            Arguments.of("Crate"),
            Arguments.of("Palette"),
            Arguments.of("Pack"),
            Arguments.of("Container1"),
            Arguments.of("BAGINBOX"),
            Arguments.of("trayyy"),
            Arguments.of("Fläschchen"),
            Arguments.of("stk"),
            Arguments.of(""),
            Arguments.of("  ")
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidMappings")
    void validMappings(String unitString, UnitOfMeasure expectedUnit) {
        UnitOfMeasureMapper mapper = new GermanUnitOfMeasureMapper();
        Assertions.assertEquals(expectedUnit, mapper.fromString(unitString));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMappings")
    void validMappings(String unitString) {
        UnitOfMeasureMapper mapper = new GermanUnitOfMeasureMapper();
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> mapper.fromString(unitString));
    }

}
