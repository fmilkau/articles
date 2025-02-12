package de.zazzam.articles.importer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public interface CsvImporter<E> {

    Collection<E> fromFile(Path path) throws IOException, CsvParserException;
    boolean canProcess(String header);

}
