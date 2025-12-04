package io.crative.backend.data.units;

public enum UnitOrganization {
    RED_CROSS("RK"),
    INVALID("INVALID");

    private String shortName;

    private UnitOrganization(String shortName) {
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
