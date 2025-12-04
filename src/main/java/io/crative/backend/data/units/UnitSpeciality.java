package io.crative.backend.data.units;

public enum UnitSpeciality {
    INVALID("INVALID");

    private String shortName;

    private UnitSpeciality(String shortName) {
        this.shortName = shortName;
    }

    public String shortName() {
        return shortName;
    }

    @Override
    public String toString() {
        return shortName;
    }
}
