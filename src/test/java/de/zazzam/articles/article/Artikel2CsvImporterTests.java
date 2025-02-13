package de.zazzam.articles.article;

import de.zazzam.articles.article.importer.Artikel2CsvImporter;
import de.zazzam.articles.importer.CsvImporter;
import de.zazzam.articles.importer.CsvParserException;
import de.zazzam.articles.price.Price;
import de.zazzam.articles.unit.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Stream;

public class Artikel2CsvImporterTests {

    private static final String testFile = "src/test/resources/002.articles.csv";

    private static Stream<Arguments> provideAllArticleData() {
        java.util.Currency eur = java.util.Currency.getInstance("EUR");
        return Stream.of(
            Arguments.of("20001", "22222", "Getränk 1", 6, UnitOfMeasure.Box, new Price(999L, eur)),
            Arguments.of("20002", "22222", "Getränk 2", 24, UnitOfMeasure.Case, new Price(1999L, eur)),
            Arguments.of("20003", "22222", "Getränk 3", 24, UnitOfMeasure.Case, new Price(2999L, eur)),
            Arguments.of("20004", "22222", "Getränk 4", 1, UnitOfMeasure.Bottle, new Price(349L, eur)),
            Arguments.of("20005", "22222", "Getränk 5", 1, UnitOfMeasure.Bottle, new Price(499L, eur)),
            Arguments.of("20006", "22222", "Getränk 6", 1, UnitOfMeasure.Bottle, new Price(199L, eur)),
            Arguments.of("20007", "22222", "Getränk 7", 10, UnitOfMeasure.Box, new Price(1449L, eur)),
            Arguments.of("20008", "22222", "Getränk 8", 10, UnitOfMeasure.Box, new Price(1299L, eur)),
            Arguments.of("20009", "22222", "Getränk 9", 20, UnitOfMeasure.Container, new Price(2999L, eur)),
            Arguments.of("20010", "22222", "Getränk 10", 25, UnitOfMeasure.Keg, new Price(1500L, eur)),
            Arguments.of("20011", "22222", "Getränk 11", 6, UnitOfMeasure.Box, new Price(799L, eur))
        );
    }

    @Test
    void canProcessValidCsv() {
        CsvImporter<Article> importer = new Artikel2CsvImporter();
        Assertions.assertTrue(importer.canProcess("PartnerID;Artikelnummer;Name;Inhalt;MEH;Preis"));
    }

    @ParameterizedTest
    @MethodSource("provideAllArticleData")
    void articleIsImported(String articleId, String partnerId, String name, Integer content, UnitOfMeasure unit, Price price) throws IOException, CsvParserException {
        CsvImporter<Article> importer = new Artikel2CsvImporter();
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
        CsvImporter<Article> importer = new Artikel2CsvImporter();
        Collection<Article> articles = importer.fromFile(Paths.get(testFile));
        Assertions.assertEquals(11, articles.size());
    }

}
