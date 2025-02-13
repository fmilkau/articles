package de.zazzam.articles.article;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleRepository articles;

    public ArticleController(ArticleRepository articles) {
        this.articles = articles;
    }

    @GetMapping("/{id}")
    public Mono<Article> getArticleById(@PathVariable String id) {
        return articles.findById(id)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

}
