package de.zazzam.articles.article;

public record Price(Integer amount, Currency currency) {

    public Price {
        if (null == amount) {
            throw new IllegalArgumentException("price amount must not be null");
        }
        if (null == currency) {
            throw new IllegalArgumentException("currency must not be null");
        }
    }

}
