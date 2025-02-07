package de.zazzam.articles.article;

import reactor.core.publisher.Mono;

public interface ArticleRepository {

    Mono<Article> findById(String id);

}
