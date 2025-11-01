package io.crative.frontend.view;

import io.crative.backend.data.units.BasicUnit;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

// TODO: make all text translateable
public class CallView extends LstView {
    private SplitPane mainSplit;
    private HBox actionBar;
    private SplitPane callCreationSplit;
    private TableView resources;

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

        resources.getItems().add(new BasicUnit(2, "RW 1-1", "Rettungswagen"));
        resources.getItems().add(new BasicUnit(4, "LF 20", "Löschgruppenfahrzeug"));

        callCreationSplit.getItems().add(information);
        callCreationSplit.getItems().add(resources);

        mainSplit.getItems().add(actionBar);
        mainSplit.getItems().add(callCreationSplit);
        mainSplit.setDividerPositions(0.05);

        root = new VBox(menuBar, mainSplit);
        root.setStyle("-fx-background-color: #1e1e1e;");
        VBox.setVgrow(mainSplit, Priority.ALWAYS);
    }

    private HBox createActionBar() {
        HBox bar = new HBox(5);
        bar.setPadding(new Insets(5));
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.getStyleClass().add("action-bar");

        String[] callCreation = {
                "Neu", "E-Ort von Anrufer", "E-Ort von Einsatz"
        };

        String[] saving = {
                "Zurücksetzen", "Speichern", "Abschließen"
        };

        String[] units = {
                "Vorschlag", "Erhöhen", "Leeren", "Disponieren"
        };

        for (String label : callCreation) {
            Button btn = new Button(label);
            btn.getStyleClass().add("toolbar-button");
            bar.getChildren().add(btn);
        }

        Separator separator = new Separator(Orientation.VERTICAL);
        bar.getChildren().add(separator);

        for (String label : saving) {
            Button btn = new Button(label);
            btn.getStyleClass().add("toolbar-button");
            bar.getChildren().add(btn);
        }

        separator = new Separator(Orientation.VERTICAL);
        bar.getChildren().add(separator);

        for (String label : units) {
            Button btn = new Button(label);
            btn.getStyleClass().add("toolbar-button");
            bar.getChildren().add(btn);
        }

        return bar;
    }

    private TableView createResourcesTable() {
        TableView table = new TableView();
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