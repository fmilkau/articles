package de.zazzam.articles.importer;

import de.zazzam.articles.article.Article;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfigurableArticleCsvImporterResolver implements CsvImporterResolver<Article> {

    private final List<CsvImporter<Article>> importers;

    public ConfigurableArticleCsvImporterResolver() {
        this.importers = new ArrayList<>();
    }

    @Override
    public Optional<CsvImporter<Article>> resolveForFile(Path path) throws IOException {
        String header = getHeader(path);
        for (CsvImporter<Article> importer : importers) {
            if (importer.canProcess(header)) {
                return Optional.of(importer);
            }
        }
        return Optional.empty();
    }

    public void addCsvImporter(CsvImporter<Article> importer) {
        importers.add(importer);
    }

    private String getHeader(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.readLine();
        }
    }

}
