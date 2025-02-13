package de.zazzam.articles.price;

import java.util.Currency;

public record Price(Long amount, Currency currency) {

    public Price {
        if (null == amount) {
            throw new IllegalArgumentException("price content must not be null");
        }
        if (null == currency) {
            throw new IllegalArgumentException("currency must not be null");
        }
    }

}
