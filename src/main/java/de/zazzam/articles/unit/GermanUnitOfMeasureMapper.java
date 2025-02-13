package de.zazzam.articles.unit;

public class GermanUnitOfMeasureMapper implements UnitOfMeasureMapper {

    @Override
    public UnitOfMeasure fromString(String unit) {
        return switch (unit.trim().toLowerCase()) {
            case "karton" -> UnitOfMeasure.Box;
            case "kasten" -> UnitOfMeasure.Case;
            case "tray" -> UnitOfMeasure.Tray;
            case "dispenser" -> UnitOfMeasure.Dispenser;
            case "bib" -> UnitOfMeasure.BagInBox;
            case "container" -> UnitOfMeasure.Container;
            case "fass" -> UnitOfMeasure.Keg;
            case "flasche" -> UnitOfMeasure.Bottle;
            case "stück" -> UnitOfMeasure.Item;
            default -> throw new IllegalArgumentException("Unknown UnitOfMeasure: " + unit);
        };
    }

}
