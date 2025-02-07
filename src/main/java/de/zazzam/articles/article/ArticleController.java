package de.zazzam.articles.article;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        return articles.findById(id);
    }

}
