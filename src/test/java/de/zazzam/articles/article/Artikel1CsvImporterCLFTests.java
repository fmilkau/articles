package de.zazzam.articles.article;

import de.zazzam.articles.article.importer.Artikel1CsvImporter;
import de.zazzam.articles.importer.CsvImporter;
import de.zazzam.articles.importer.CsvParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Stream;

public class Artikel1CsvImporterCLFTests {

    private static final String testFile = "src/test/resources/001.articles.csv";

    private static Stream<Arguments> provideAllArticleData() {
        return Stream.of(
            Arguments.of("10001", "11111", "Artikel 1", 6, UnitOfMeasure.Box, new Price(748920, Currency.CLF)),
            Arguments.of("10002", "11111", "Artikel 2", 24, UnitOfMeasure.Case, new Price(117284, Currency.CLF)),
            Arguments.of("10003", "11111", "Artikel 3", 24, UnitOfMeasure.Tray, new Price(392948, Currency.CLF)),
            Arguments.of("10004", "11111", "Artikel 4", 1, UnitOfMeasure.Box, new Price(762665, Currency.CLF)),
            Arguments.of("10005", "11111", "Artikel 5", 1, UnitOfMeasure.Box, new Price(1298383, Currency.CLF)),
            Arguments.of("10006", "11111", "Artikel 6", 1, UnitOfMeasure.Box, new Price(710292, Currency.CLF)),
            Arguments.of("10007", "11111", "Artikel 7", 10, UnitOfMeasure.Dispenser, new Price(191065, Currency.CLF)),
            Arguments.of("10008", "11111", "Artikel 8", 10, UnitOfMeasure.BagInBox, new Price(896464, Currency.CLF)),
            Arguments.of("10009", "11111", "Artikel 9", 20, UnitOfMeasure.Container, new Price(324767, Currency.CLF)),
            Arguments.of("10010", "11111", "Artikel 10", 25, UnitOfMeasure.Keg, new Price(958996, Currency.CLF)),
            Arguments.of("10011", "11111", "Artikel 11", 6, UnitOfMeasure.Box, new Price(779028, Currency.CLF)),
            Arguments.of("10012", "11111", "Artikel 12", 30, UnitOfMeasure.Keg, new Price(819444, Currency.CLF)),
            Arguments.of("10013", "11111", "Artikel 13", 6, UnitOfMeasure.Box, new Price(712914, Currency.CLF)),
            Arguments.of("10014", "11111", "Artikel 14", 30, UnitOfMeasure.Keg, new Price(876003, Currency.CLF)),
            Arguments.of("10015", "11111", "Artikel 15", 24, UnitOfMeasure.Box, new Price(895560, Currency.CLF)),
            Arguments.of("10016", "11111", "Artikel 16", 24, UnitOfMeasure.Box, new Price(109436, Currency.CLF)),
            Arguments.of("10017", "11111", "Artikel 17", 1, UnitOfMeasure.Item, new Price(0, Currency.CLF)),
            Arguments.of("10018", "11111", "Artikel 18", 6, UnitOfMeasure.Box, new Price(1563954, Currency.CLF)),
            Arguments.of("10019", "11111", "Artikel 19", 6, UnitOfMeasure.Box, new Price(80000000, Currency.CLF))
        );
    }

    @Test
    void canProcessValidCsv() {
        CsvImporter<Article> importer = new Artikel1CsvImporter(Currency.CLF);
        Assertions.assertTrue(importer.canProcess("\uFEFFPartnerID;ArtNr;ArtBez;Inhalt;MEH;VK"));
    }

    @ParameterizedTest
    @MethodSource("provideAllArticleData")
    void articleIsImported(String articleId, String partnerId, String name, Integer content, UnitOfMeasure unit, Price price) throws IOException, CsvParserException {
        CsvImporter<Article> importer = new Artikel1CsvImporter(Currency.CLF);
        Collection<Article> articles = importer.fromFile(Paths.get(testFile));
        Article article = articles.stream().filter(a -> a.articleId().equals(articleId)).findFirst().get();
        Assertions.assertEquals(articleId, article.articleId());
        Assertions.assertEquals(partnerId, article.partnerId());
        Assertions.assertEquals(name, article.name());
        Assertions.assertEquals(content, article.content());
        Assertions.assertEquals(unit, article.unit());
        Assertions.assertEquals(price.amount(), article.price().amount());
        Assertions.assertEquals(price.currency(), article.price().currency());
    }

    @Test
    void correctNumberOfArticles() throws IOException, CsvParserException {
        CsvImporter<Article> importer = new Artikel1CsvImporter(Currency.CLF);
        Collection<Article> articles = importer.fromFile(Paths.get(testFile));
        Assertions.assertEquals(19, articles.size());
    }

}
