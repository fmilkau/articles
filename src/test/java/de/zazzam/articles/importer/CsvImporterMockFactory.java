package de.zazzam.articles.importer;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Function;

public class CsvImporterMockFactory {

    public static <E> CsvImporter<E> mockCanProcess(Function<String, Boolean> canProcessFn) {
        return new CsvImporter<E>() {
            @Override
            public Collection<E> fromFile(Path path) {
                throw new RuntimeException("fromFile is not implemented on this mock");
            }

            @Override
            public boolean canProcess(String header) {
                return canProcessFn.apply(header);
            }
        };
    }

}
