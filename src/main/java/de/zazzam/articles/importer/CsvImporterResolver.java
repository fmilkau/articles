package de.zazzam.articles.importer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public interface CsvImporterResolver<E> {

    Optional<CsvImporter<E>> resolveForFile(Path path) throws IOException;

}
