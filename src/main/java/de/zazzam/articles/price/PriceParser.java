package de.zazzam.articles.price;

import java.text.ParseException;

public interface PriceParser {

    Price fromString(String priceString) throws ParseException;

}
