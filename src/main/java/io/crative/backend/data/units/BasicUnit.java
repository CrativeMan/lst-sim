package io.crative.backend.data.units;

public class BasicUnit {
    private int status;
    private String radioName;
    private String unitType;

    public BasicUnit(int status, String radioName, String unitType) {
        this.status = status;
        this.radioName = radioName;
        this.unitType = unitType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRadioName() {
        return radioName;
    }

    public void setRadioName(String radioName) {
        this.radioName = radioName;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
}
