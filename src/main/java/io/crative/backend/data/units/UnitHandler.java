package io.crative.backend.data.units;

import java.util.ArrayList;
import java.util.List;

/*
    - Store all available units
    - Give out information about units
    - Handle interaction with specific units
 */
public class UnitHandler {
    private static List<BasicUnit> allUnits = new ArrayList<>();

    public static void addUnit(BasicUnit unit) {
        if (allUnits.contains(unit)) {
            System.err.println("UnitHandler: Unit already exists");
            return;
        }
        allUnits.add(unit);
        System.out.println("UnitHandler: Added unit " + unit.getRadioName() + " to the list");
    }

    public static List<BasicUnit> getAllUnits() {
        return allUnits;
    }
}
