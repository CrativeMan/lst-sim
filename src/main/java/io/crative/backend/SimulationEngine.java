package io.crative.backend;

import io.crative.backend.time.DebugTimeListener;
import io.crative.backend.time.TimeManager;
import io.crative.event.EventBus;
import io.crative.event.ui.ShowAlertEvent;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;

public class SimulationEngine {
    private DebugTimeListener debugTimeListener = new DebugTimeListener();

    private AnimationTimer gameLoop;
    private long lastUpdate = 0;
    // other managers/schedulers

    private static final long UPDATE_INTERVAL_NANOS = 1_000_000_000L / 60; // ticks per second

    public void start() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (lastUpdate == 0) {
                    lastUpdate = l;
                    return;
                }

                long elapsedNanos = l - lastUpdate;

                if (elapsedNanos >= UPDATE_INTERVAL_NANOS) {
                    update();
                    lastUpdate = l;
                }
            }
        };
        EventBus.getInstance().postOnUIThread(new ShowAlertEvent("test", Alert.AlertType.ERROR));
        gameLoop.start();
    }

    private void update() {
        TimeManager.getInstance().update();

        // Update other managers/schedulers
    }

    public void pause() {
        TimeManager.getInstance().pause();
    }

    public void resume() {
        TimeManager.getInstance().resume();
    }

    public void setSpeed(double multiplier) {
        TimeManager.getInstance().setSpeed(multiplier);
    }

    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}
