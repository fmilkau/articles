package de.zazzam.articles.importer;

public class CsvParserException extends Exception {

    public CsvParserException(String message) {
        super(message);
    }

    public CsvParserException(String message, Throwable cause) {
        super(message, cause);
    }

}
