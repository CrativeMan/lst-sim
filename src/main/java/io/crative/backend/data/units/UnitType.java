package io.crative.backend.data.units;

public enum UnitType {
    ITW_NAW("Intensiv Transportwagen / Notarztwagen", "ITW/NAW", "70"),
    RTW("Rettungswagen", "RTW", "71"),
    KTW("Krankentransportwagen", "KTW", "72"),
    KTW_B("Krankentransportwagen Typ B", "KTW_B", "73"),
    G_RTW("Großraum Rettungswagen", "G-RTW", "75"),
    NEF_VEF("Notarzt Einsatzfahrzeug / Verlegungsarzt Einsatzfahrzeug", "NEF/VEF", "76"),
    FR_HVO("First Responder / Helfer vor Ort", "FR/HvO", "79"),
    INVALID("INVALID", "INVALID", "INVALID");

    public String name;
    public String kurzName;
    public String funkName;

    private UnitType(String name, String kurzName, String funkName) {
        this.name = name;
        this.kurzName = kurzName;
        this.funkName = funkName;
    }

    @Override
    public String toString() {
        return kurzName;
    }
}
