package de.zazzam.articles.article;

import com.google.common.collect.ImmutableMap;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class ImmutableArticleRepository implements ArticleRepository {

    private final Map<String, Article> articles;

    public ImmutableArticleRepository(Collection<Article> articles) {
        this.articles = ImmutableMap.copyOf(articles.stream()
                .collect(Collectors.toMap(Article::articleId, article -> article)));
    }

    @Override
    public Mono<Article> findById(String id) {
        return Mono.justOrEmpty(articles.get(id));
    }

}
