package de.zazzam.articles.article;

public enum Currency {

    EUR(2),
    USD(2),
    CLF(4); // Unidad de Fomento - https://en.wikipedia.org/wiki/Unidad_de_Fomento

    public final Integer digits;

    Currency(Integer digits) {
        this.digits = digits;
    }

}
