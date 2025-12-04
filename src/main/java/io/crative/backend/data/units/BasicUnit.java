package io.crative.backend.data.units;

public class BasicUnit {
    private UnitOrganization organization;
    private String location;
    private UnitSpeciality speciality;
    private UnitType unitType;
    private int ongoingNumber;

    private UnitStatus status;
    private String radioName;

    public BasicUnit(UnitOrganization organization, String location, UnitSpeciality speciality, UnitType unitType, int ongoingNumber) {
        this.organization = organization;
        this.location = location;
        this.speciality = speciality;
        this.unitType = unitType;
        this.ongoingNumber = ongoingNumber;

        this.status = UnitStatus.E_BEREIT_WACHE;
        this.radioName = generateRadioName();
    }

    private String generateRadioName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.organization.shortName());
        sb.append(" ");
        sb.append(this.location);
        sb.append(" ");
        if (this.speciality != UnitSpeciality.INVALID) {
            sb.append(this.speciality.shortName());
        }
        sb.append("/");
        sb.append(this.unitType.kurzName);
        sb.append("/");
        sb.append(ongoingNumber);

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return this.radioName.hashCode();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Getter and Setter

    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    public UnitOrganization getOrganization() {
        return organization;
    }

    public String getLocation() {
        return location;
    }

    public UnitSpeciality getSpeciality() {
        return speciality;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public int getOngoingNumber() {
        return ongoingNumber;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public String getRadioName() {
        return radioName;
    }
}
