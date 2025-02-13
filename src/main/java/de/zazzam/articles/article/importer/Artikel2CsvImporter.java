package de.zazzam.articles.article.importer;

import de.zazzam.articles.price.PriceParserImpl;
import de.zazzam.articles.unit.GermanUnitOfMeasureMapper;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class Artikel2CsvImporter extends AbstractArticleCsvImporter {

    public Artikel2CsvImporter() {
        super(
            List.of("PartnerID", "Artikelnummer", "Name", "Inhalt", "MEH", "Preis"),
            new PriceParserImpl(Currency.getInstance("EUR"), NumberFormat.getInstance(Locale.GERMANY)),
            new GermanUnitOfMeasureMapper()
        );
    }

}
