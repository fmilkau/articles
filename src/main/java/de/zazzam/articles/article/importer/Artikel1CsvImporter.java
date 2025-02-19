package de.zazzam.articles.article.importer;

import de.zazzam.articles.price.PriceParserImpl;
import de.zazzam.articles.unit.GermanUnitOfMeasureMapper;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class Artikel1CsvImporter extends AbstractArticleCsvImporter {

    public Artikel1CsvImporter(Currency currency) {
        super(
            List.of("PartnerID", "ArtNr", "ArtBez", "Inhalt", "MEH", "VK"),
            new PriceParserImpl(currency, NumberFormat.getInstance(Locale.GERMANY)),
            new GermanUnitOfMeasureMapper()
        );
    }

}
