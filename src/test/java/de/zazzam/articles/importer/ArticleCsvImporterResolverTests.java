package de.zazzam.articles.importer;

import de.zazzam.articles.article.Article;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ArticleCsvImporterResolverTests {

    private static final String testResourcesRoot = "src/test/resources";

    private static Stream<Arguments> provideValidCsvFilePaths() {
        return Stream.of(
            Arguments.of(Paths.get(testResourcesRoot, "001.articles.csv").toAbsolutePath()),
            Arguments.of(Paths.get(testResourcesRoot, "002.articles.csv").toAbsolutePath())
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidCsvFilePaths")
    void returnsEmptyIfNoMatchesFound(Path path) throws IOException {
        ConfigurableArticleCsvImporterResolver resolver = new ConfigurableArticleCsvImporterResolver();
        resolver.addCsvImporter(getPerfectlyIncompatibleImporter());
        Assertions.assertTrue(resolver.resolveForFile(path).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideValidCsvFilePaths")
    void returnsImporterWhenMatchIsFound(Path path) throws IOException {
        ConfigurableArticleCsvImporterResolver resolver = new ConfigurableArticleCsvImporterResolver();
        resolver.addCsvImporter(getPerfectlyCompatibleImporter());
        Assertions.assertTrue(resolver.resolveForFile(path).isPresent());
    }

    @Test
    void article001ImporterCorrectlyResolved() throws IOException {
        CsvImporter<Article> importer001 = getArticles001Importer();
        CsvImporter<Article> importer002 = getArticles002Importer();
        ConfigurableArticleCsvImporterResolver resolver = new ConfigurableArticleCsvImporterResolver();
        resolver.addCsvImporter(importer001);
        resolver.addCsvImporter(importer002);
        Path articles001 = Paths.get(testResourcesRoot, "001.articles.csv").toAbsolutePath();
        Assertions.assertEquals(importer001, resolver.resolveForFile(articles001).orElse(null));
    }

    @Test
    void article002ImporterCorrectlyResolved() throws IOException {
        CsvImporter<Article> importer001 = getArticles001Importer();
        CsvImporter<Article> importer002 = getArticles002Importer();
        ConfigurableArticleCsvImporterResolver resolver = new ConfigurableArticleCsvImporterResolver();
        resolver.addCsvImporter(importer001);
        resolver.addCsvImporter(importer002);
        Path articles002 = Paths.get(testResourcesRoot, "002.articles.csv").toAbsolutePath();
        Assertions.assertEquals(importer002, resolver.resolveForFile(articles002).orElse(null));
    }

    @Test
    void throwsNoSuchFileExceptionForMissingFile() {
        ConfigurableArticleCsvImporterResolver resolver = new ConfigurableArticleCsvImporterResolver();
        resolver.addCsvImporter(getPerfectlyCompatibleImporter());
        resolver.addCsvImporter(getPerfectlyIncompatibleImporter());
        Path doesNotExist = Paths.get(testResourcesRoot, "does-not-exist.articles.csv");
        Assertions.assertThrowsExactly(NoSuchFileException.class, () -> resolver.resolveForFile(doesNotExist));
    }

    private CsvImporter<Article> getPerfectlyIncompatibleImporter() {
        CsvImporter<Article> importer = Mockito.mock(CsvImporter.class);
        Mockito.when(importer.canProcess(Mockito.anyString())).thenReturn(false);
        return importer;
    }

    private CsvImporter<Article> getPerfectlyCompatibleImporter() {
        CsvImporter<Article> importer = Mockito.mock(CsvImporter.class);
        Mockito.when(importer.canProcess(Mockito.anyString())).thenReturn(true);
        return importer;
    }

    private CsvImporter<Article> getArticles001Importer() {
        return CsvImporterMockFactory.mockCanProcess(header ->
            header.equals("\uFEFFPartnerID;ArtNr;ArtBez;Inhalt;MEH;VK"));
    }

    private CsvImporter<Article> getArticles002Importer() {
        return CsvImporterMockFactory.mockCanProcess(header ->
            header.equals("\uFEFFPartnerID;Artikelnummer;Name;Inhalt;MEH;Preis"));
    }

}
