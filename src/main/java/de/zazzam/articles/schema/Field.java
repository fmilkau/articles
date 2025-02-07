package de.zazzam.articles.schema;

public interface Field<E> {

    String getIdentifier();
    E getValue();

}
