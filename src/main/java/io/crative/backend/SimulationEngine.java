package io.crative.backend;

import io.crative.backend.time.TimeManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private TimeManager timeManager;
    // other managers/schedulers

    private ScheduledExecutorService gameLoop;
    private static final int TICK_RATE = 60; // ticks per second

    public void start() {
        gameLoop = Executors.newSingleThreadScheduledExecutor();
        gameLoop.scheduleAtFixedRate(
                this::update,
                0,
                1000 / TICK_RATE,
                TimeUnit.MILLISECONDS
        );
    }

    private void update() {
        timeManager.update();
    }

    public void pause() {
        timeManager.pause();
    }

    public void resume() {
        timeManager.resume();
    }

    public void setSpeed(double multiplier) {
        timeManager.setSpeed(multiplier);
    }

    public void stop() {
        if (gameLoop != null && !gameLoop.isShutdown()) {
            gameLoop.shutdown();
        }
    }
}
