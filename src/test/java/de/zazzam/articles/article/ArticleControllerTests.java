package de.zazzam.articles.article;

import de.zazzam.articles.price.Price;
import de.zazzam.articles.unit.UnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Currency;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ArticleController.class)
public class ArticleControllerTests {

    @Autowired
    private WebTestClient client;

    @MockitoBean
    private ArticleRepository articles;

    @Test
    void getArticleById() {
        String id = "20005";
        Article article = new Article(
            "20005",
            "22222",
            "Getränk 5",
            1,
            UnitOfMeasure.Bottle,
            new Price(499L, Currency.getInstance("EUR"))
        );
        Mockito.when(articles.findById(id)).thenReturn(Mono.just(article));
        client.get().uri("/articles/{id}", id)
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Article.class);
        Mockito.verify(articles, Mockito.times(1)).findById(id);
    }

    @Test
    void failOnUnknownArticle() {
        String id = "12345";
        Mockito.when(articles.findById(id)).thenReturn(Mono.empty());
        client.get().uri("/articles/{id}", id)
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isNotFound();
        Mockito.verify(articles, Mockito.times(1)).findById(id);
    }

    @Test
    void failOnUnknownError() {
        String id = "12345";
        Mockito.when(articles.findById(id)).thenThrow(RuntimeException.class);
        client.get().uri("/articles/{id}", id)
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().is5xxServerError();
        Mockito.verify(articles, Mockito.times(1)).findById(id);
    }

}
