package io.crative;

import io.crative.backend.SimulationEngine;
import io.crative.frontend.FrontEnd;
import javafx.application.Application;
import javafx.stage.Stage;

public class LstSim extends Application {
    FrontEnd frontEnd;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        frontEnd = new FrontEnd();
        frontEnd.initialize(stage);
        SimulationEngine engine = new SimulationEngine();
        engine.start();
    }

}