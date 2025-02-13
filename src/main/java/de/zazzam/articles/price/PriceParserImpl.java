package de.zazzam.articles.price;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;

public class PriceParserImpl implements PriceParser {

    private final Currency currency;
    private final NumberFormat format;

    public PriceParserImpl(Currency currency, NumberFormat format) {
        this.currency = currency;
        this.format = format;
    }

    @Override
    public Price fromString(String priceString) throws ParseException {
        Number amount = format.parse(priceString);
        Long minorAmount = Math.round(amount.doubleValue() * Math.pow(10, currency.getDefaultFractionDigits()));
        return new Price(minorAmount, currency);
    }

}
