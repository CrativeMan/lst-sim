package io.crative.backend.time;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TimeManager {
    private LocalDateTime gameTime;
    private boolean paused;
    private double speedMultiplier; // 1.0 real time 2.0 double time
    private long lastUpdateMilis;
    private final List<TimeListener> listeners;

    public static TimeManager instance = new TimeManager();

    private TimeManager() {
        this.gameTime = LocalDateTime.now();
        this.paused = false;
        this.speedMultiplier = 1.0;
        this.lastUpdateMilis = System.currentTimeMillis();
        this.listeners = new ArrayList<>();
    }

    public static TimeManager getInstance() {
        return instance;
    }

    public void update() {
        if (paused) return;

        long now = System.currentTimeMillis();
        long elapsed = now - lastUpdateMilis;
        lastUpdateMilis = now;

        long gameElapsed = (long) (elapsed * speedMultiplier);
        gameTime = gameTime.plus(gameElapsed, ChronoUnit.MILLIS);
        notifyTimeChanged();
    }

    public void pause() {
        if (paused) {
            System.out.println("TimeManager: The Game Time is already paused");
            return;
        }
        paused = true;
        notifyOnTimePaused();
    }

    public void resume() {
        if (!paused) {
            System.out.println("TimeManager: The Game Time is already running");
            return;
        }
        paused = false;
        notifyOnTimeResumed();
    }

    public void setSpeed(double multiplier) {
        speedMultiplier = multiplier;
        notifySpeedChanged(multiplier);
    }

    public LocalDateTime getGameTime() {
        return gameTime;
    }

    // =============================================================================================================
    // Listener Management
    public void registerListener(TimeListener listener) {
        if (this.listeners.contains(listener)) {
            System.err.println("TimeManager: This listener is already registered: " + listener.getName());
            return;
        }
        System.out.println("TimeManager: Registered TimeListener: " + listener.getName());
        this.listeners.add(listener);
    }

    public void unregisterListener(TimeListener listener) {
        if (!this.listeners.contains(listener)) {
            System.err.println("TimeManager: This listener is not registered: " + listener.getName());
            return;
        }
        System.out.println("TimeManager: Unregistered TimeListener: " + listener.getName());
        this.listeners.remove(listener);
    }

    private void notifyTimeChanged() {
        for (TimeListener listener : listeners) {
            listener.onTimeChanged(gameTime);
        }
    }

    private void notifyOnTimePaused() {
        for (TimeListener listener : listeners) {
            listener.onTimePaused();
        }
    }

    private void notifyOnTimeResumed() {
        for (TimeListener listener : listeners) {
            listener.onTimeResumed();
        }
    }

    private void notifySpeedChanged(double speedMultiplier) {
        for (TimeListener listener : listeners) {
            listener.onSpeedChanged(speedMultiplier);
        }
    }
}
