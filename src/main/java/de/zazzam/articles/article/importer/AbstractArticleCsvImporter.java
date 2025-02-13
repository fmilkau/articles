package de.zazzam.articles.article.importer;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import de.zazzam.articles.article.Article;
import de.zazzam.articles.unit.UnitOfMeasure;
import de.zazzam.articles.importer.CsvImporter;
import de.zazzam.articles.importer.CsvParserException;
import de.zazzam.articles.price.Price;
import de.zazzam.articles.price.PriceParser;
import de.zazzam.articles.unit.UnitOfMeasureMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractArticleCsvImporter implements CsvImporter<Article> {

    protected final Splitter lineSplitter;
    protected final List<String> headerColumns;
    protected final PriceParser priceParser;
    protected final UnitOfMeasureMapper unitMapper;

    protected AbstractArticleCsvImporter(List<String> header, PriceParser priceParser, UnitOfMeasureMapper unitMapper) {
        CharMatcher illegalCharacters = CharMatcher.whitespace()
            .or(CharMatcher.breakingWhitespace())
            .or(CharMatcher.javaIsoControl())
            .or(CharMatcher.invisible());
        lineSplitter = Splitter.on(';')
            .omitEmptyStrings()
            .trimResults(illegalCharacters);
        this.headerColumns = header;
        this.priceParser = priceParser;
        this.unitMapper = unitMapper;
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
        try {
            return unitMapper.fromString(identifier);
        } catch (IllegalArgumentException e) {
            throw new CsvParserException("cannot parse unit of measure:" + identifier, e);
        }
    }

    protected Price parsePrice(String price) throws CsvParserException {
        try {
            return priceParser.fromString(price);
        } catch (ParseException e) {
            throw new CsvParserException("unable to parse price: " + price, e);
        }
    }

}
