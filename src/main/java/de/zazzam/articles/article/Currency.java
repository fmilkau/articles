package de.zazzam.articles.article;

public enum Currency {

    EUR(2),
    USD(2);

    public final Integer digits;

    Currency(Integer digits) {
        this.digits = digits;
    }

}
