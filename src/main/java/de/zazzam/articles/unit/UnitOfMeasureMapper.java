package de.zazzam.articles.unit;

public interface UnitOfMeasureMapper {

    UnitOfMeasure fromString(String unit) throws IllegalArgumentException;

}
