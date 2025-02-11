package de.zazzam.articles.article;

import de.zazzam.articles.importer.CsvImporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Configuration
public class ArticleConfiguration {

    @Value("${zazzam.article.data-root}")
    private String dataSourceRoot;

    @Bean
    public ArticleRepository articleRepository() throws IOException {
        return new ImmutableArticleRepository(loadArticles());
    }

    private List<Article> loadArticles() throws IOException {
        CsvImporter<Article> articleImporter = getCsvImporter();
        List<Article> articles = new ArrayList<>();
        for (Path path : getCandidateFiles()) {
            articles.addAll(articleImporter.fromFile(path));
        }
        // TODO - deal with duplicates? -> inside csv importer
        return articles;
    }

    private CsvImporter<Article> getCsvImporter() {
        return null;
    }

    private List<Path> getCandidateFiles() throws IOException {
        try (Stream<Path> children = Files.list(Paths.get(dataSourceRoot))) {
            return children.filter(Files::isRegularFile)
                    .filter(path -> path.endsWith(".csv"))
                    .toList();
        }
    }

}
