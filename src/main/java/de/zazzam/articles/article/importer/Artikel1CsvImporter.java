package de.zazzam.articles.article.importer;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import de.zazzam.articles.article.Article;
import de.zazzam.articles.article.Currency;
import de.zazzam.articles.article.Price;
import de.zazzam.articles.article.UnitOfMeasure;
import de.zazzam.articles.importer.CsvImporter;
import de.zazzam.articles.importer.CsvParserException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Artikel1CsvImporter implements CsvImporter<Article> {

    private final Splitter lineSplitter;
    private final Splitter currencySplitter;
    private final List<String> headerColumns;
    private final Currency currency;

    public Artikel1CsvImporter(Currency currency) {
        CharMatcher illegalCharacters = CharMatcher.whitespace()
            .or(CharMatcher.breakingWhitespace())
            .or(CharMatcher.javaIsoControl())
            .or(CharMatcher.invisible());
        lineSplitter = Splitter.on(';')
            .omitEmptyStrings()
            .trimResults(illegalCharacters);
        currencySplitter = Splitter.on(',');
        headerColumns = List.of("PartnerID", "ArtNr", "ArtBez", "Inhalt", "MEH", "VK");
        this.currency = currency;
    }

    @Override
    public Collection<Article> fromFile(Path path) throws IOException, CsvParserException {
        try (Stream<String> lines = Files.lines(path)) {
            List<Article> articles = new ArrayList<>();
            int index = 0;
            for (Iterator<String> it = lines.iterator(); it.hasNext(); ) {
                String line = it.next();
                index += 1;
                if (index == 1 && canProcess(line)) { // check first line if header matches configured header
                    continue;
                } else if (index == 1) { // throw parser error if header doesn't match
                    throw new CsvParserException("header mismatch");
                }
                if (!line.isEmpty()) { // only process non-empty lines
                    try {
                        articles.add(parseArticle(line));
                    } catch (CsvParserException e) { // wrap parser exceptions to include line number and path
                        throw new CsvParserException("unable to parse line " + index + " in file " + path, e);
                    }
                }
            }
            return articles;
        }
    }

    @Override
    public boolean canProcess(String header) {
        List<String> columns = lineSplitter.splitToList(header);
        if (columns.size() != this.headerColumns.size()) { // must have same number of fields
            return false;
        }
        for (int i = 0; i < headerColumns.size(); i++) { // field names must match in content and order
            if (!headerColumns.get(i).equals(columns.get(i))) {
                return false;
            }
        }
        return true;
    }

    protected Article parseArticle(String line) throws CsvParserException {
        List<String> fields = lineSplitter.splitToList(line);
        if (fields.size() < headerColumns.size()) {
            throw new CsvParserException("invalid number of fields");
        }
        return new Article(
            fields.get(1),  // partnerId
            fields.get(0),  // articleId
            fields.get(2),  // name
            Integer.parseInt(fields.get(3)),
            parseUnit(fields.get(4)),
            parsePrice(fields.get(5))
        );
    }

    protected UnitOfMeasure parseUnit(String identifier) throws CsvParserException {
        return switch (identifier) {
            case "BIB" -> UnitOfMeasure.BagInBox;
            case "Container" -> UnitOfMeasure.Container;
            case "Dispenser" -> UnitOfMeasure.Dispenser;
            case "Fass" -> UnitOfMeasure.Keg;
            case "Karton" -> UnitOfMeasure.Box;
            case "Kasten" -> UnitOfMeasure.Case;
            case "Stück" -> UnitOfMeasure.Item;
            case "Tray" -> UnitOfMeasure.Tray;
            default -> throw new CsvParserException("unknown unit of measure: " + identifier);
        };
    }

    protected Price parsePrice(String price) throws CsvParserException {
        List<String> priceParts = currencySplitter.splitToList(price);
        if (priceParts.size() > 2) {
            throw new CsvParserException("malformed price: " + price);
        } else {
            String major = priceParts.getFirst();
            String minor = priceParts.size() == 2
                ? Strings.padEnd(priceParts.get(1), currency.digits, '0') // pad to minor currency digit length
                : Strings.padStart("", currency.digits, '0'); // default to zeros ('0') in the length of minor currency
            if (minor.length() > currency.digits) { // round before returning
                String doubleRepresentation = major + minor.substring(0, currency.digits) + '.' + minor.substring(currency.digits);
                return new Price((int) Math.round(Double.parseDouble(doubleRepresentation)), currency);
            } else { // simply parse Integer representation
                return new Price(Integer.parseInt(priceParts.getFirst() + minor), currency);
            }
        }
    }

}
