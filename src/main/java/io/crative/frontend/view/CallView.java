package io.crative.frontend.view;

import io.crative.backend.data.units.*;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

// TODO: make all text translateable
public class CallView extends LstView {
    private SplitPane mainSplit;
    private MenuBar actionBar;
    private SplitPane callCreationSplit;
    private TableView<BasicUnit> resources;

    public CallView(MenuBar menuBar) {
        createLayout(menuBar);
    }

    private void createLayout(MenuBar menuBar) {
        mainSplit = new SplitPane();
        mainSplit.setOrientation(Orientation.VERTICAL);

        actionBar = createActionBar();

        callCreationSplit = new SplitPane();
        callCreationSplit.setOrientation(Orientation.HORIZONTAL);

        VBox information = new VBox();

        resources = createResourcesTable();
        // TODO: remove
        UnitHandler.addUnit(new BasicUnit(UnitOrganization.INVALID, "FFB", UnitSpeciality.INVALID, UnitType.RTW, 1));
        UnitHandler.addUnit(new BasicUnit(UnitOrganization.INVALID, "FFB", UnitSpeciality.INVALID, UnitType.RTW, 2));
        UnitHandler.addUnit(new BasicUnit(UnitOrganization.INVALID, "FFB", UnitSpeciality.INVALID, UnitType.KTW, 1));
        UnitHandler.addUnit(new BasicUnit(UnitOrganization.INVALID, "FFB", UnitSpeciality.INVALID, UnitType.KTW, 2));
        UnitHandler.addUnit(new BasicUnit(UnitOrganization.INVALID, "FFB", UnitSpeciality.INVALID, UnitType.KTW, 3));
        resources.getItems().addAll(UnitHandler.getAllUnits());

        callCreationSplit.getItems().add(information);
        callCreationSplit.getItems().add(resources);

        mainSplit.getItems().add(actionBar);
        mainSplit.getItems().add(callCreationSplit);
        mainSplit.setDividerPositions(0.05);

        root = new VBox(menuBar, mainSplit);
        root.setStyle("-fx-background-color: #1e1e1e;");
        VBox.setVgrow(mainSplit, Priority.ALWAYS);
    }

    private MenuBar createActionBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.getStyleClass().add("action-bar");

        String[] callCreation = {
                "Neu", "E-Ort von Anrufer", "E-Ort von Einsatz"
        };

        String[] saving = {
                "Zurücksetzen", "Speichern", "Abschließen"
        };

        String[] units = {
                "Vorschlag", "Erhöhen", "Leeren", "Disponieren"
        };

        Menu callCreationMenu = new Menu("Einsatzanlage");
        for (String label : callCreation) {
            MenuItem item = new MenuItem(label);
            callCreationMenu.getItems().add(item);
        }

        Menu savingMenu = new Menu("Speichern");
        for (String label : saving) {
            MenuItem item = new MenuItem(label);
            savingMenu.getItems().add(item);
        }

        Menu unitsMenu = new Menu("Einheiten");
        for (String label : units) {
            MenuItem item = new MenuItem(label);
            unitsMenu.getItems().add(item);
        }

        menuBar.getMenus().addAll(callCreationMenu, savingMenu, unitsMenu);

        return menuBar;
    }

    private TableView<BasicUnit> createResourcesTable() {
        TableView<BasicUnit> table = new TableView<BasicUnit>();
        table.setPlaceholder(new Label("Keine Ressourcen verfügbar"));

        TableColumn<BasicUnit, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableColumn<BasicUnit, String> radioNameCol = new TableColumn<>("Funk Rufname");
        radioNameCol.setCellValueFactory(new PropertyValueFactory<>("radioName"));
        TableColumn<BasicUnit, String> unitTypeCol = new TableColumn<>("Einheitentyp");
        unitTypeCol.setCellValueFactory(new PropertyValueFactory<>("unitType"));

        table.getColumns().add(statusCol);
        table.getColumns().add(radioNameCol);
        table.getColumns().add(unitTypeCol);

        return table;
    }
}