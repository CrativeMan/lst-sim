package io.crative.backend.data.units;

public enum UnitStatus {
    PRIORITY_SPRECHWUNSCH("Prioritäten Sprechwunsch", 0),
    E_BEREIT_FUNK("E-Bereit über Funk", 1),
    E_BEREIT_WACHE("E-Bereit auf Wache", 2),
    E_UEBERNOMMEN("E-Übernommen", 3),
    EINSATZORT("E-Ort", 4),
    SPRECHWUNSCH("Sprechwunsch", 5),
    NICHT_E_BEREIT("Nicht E-Bereit", 6),
    E_GEBUNDEN("E-Gebunden", 7),
    BED_VERFUEGBAR("Bed. verfügbar", 8),
    INVALID("INVALID", -1);

    private String description;
    private int number;

    private UnitStatus(String description, int number) {
        this.description = description;
        this.number = number;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return ""+number;
    }
}
