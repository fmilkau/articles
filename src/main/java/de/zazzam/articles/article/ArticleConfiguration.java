package de.zazzam.articles.article;

import de.zazzam.articles.article.importer.Artikel1CsvImporter;
import de.zazzam.articles.importer.ConfigurableArticleCsvImporterResolver;
import de.zazzam.articles.importer.CsvImporter;
import de.zazzam.articles.importer.CsvImporterResolver;
import de.zazzam.articles.importer.CsvParserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Configuration
public class ArticleConfiguration {

    @Value("${zazzam.articles.data-root}")
    private String dataSourceRoot;

    @Bean
    public ArticleRepository articleRepository() throws IOException, CsvParserException {
        return new ImmutableArticleRepository(loadArticles());
    }

    private List<Article> loadArticles() throws IOException, CsvParserException {
        CsvImporterResolver<Article> importerResolver = importerResolver();
        List<Article> articles = new ArrayList<>();
        for (Path path : getCandidateFiles()) {
            Optional<CsvImporter<Article>> importer = importerResolver.resolveForFile(path);
            if (importer.isPresent()) {
                articles.addAll(importer.get().fromFile(path));
            } else {
                throw new RuntimeException("no csv importer found for file " + path);
            }
        }
        return articles;
    }

    private CsvImporterResolver<Article> importerResolver() {
        ConfigurableArticleCsvImporterResolver resolver = new ConfigurableArticleCsvImporterResolver();
        resolver.addCsvImporter(new Artikel1CsvImporter(Currency.CLF));
        return resolver;
    }

    private CsvImporter<Article> getCsvImporter() {
        return null;
    }

    private List<Path> getCandidateFiles() throws IOException {
        try (Stream<Path> children = Files.list(Paths.get(dataSourceRoot).toAbsolutePath())) {
            return children.filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().endsWith(".csv"))
                .toList();
        }
    }

}
