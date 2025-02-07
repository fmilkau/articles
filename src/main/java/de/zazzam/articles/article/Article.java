package de.zazzam.articles.article;

public record Article(String articleId,
                      String partnerId,
                      String name,
                      Integer amount,
                      ArticleUnit unit,
                      Price price) {

    public Article {
        if (articleId == null || articleId.trim().isEmpty()) {
            throw new IllegalArgumentException("article id must be neither null nor empty");
        }
        if (partnerId == null || partnerId.trim().isEmpty()) {
            throw new IllegalArgumentException("partner id must be neither null nor empty");
        }
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("amount must not be null and >= 0");
        }
        if (unit == null) {
            throw new IllegalArgumentException("unit must not be null");
        }
        if (price == null) {
            throw new IllegalArgumentException("price must not be null");
        }
    }

}
