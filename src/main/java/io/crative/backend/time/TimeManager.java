package io.crative.backend.time;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TimeManager {
    private LocalDateTime gameTime;
    private boolean paused;
    private double speedMultiplier; // 1.0 real time 2.0 double time
    private long lastUpdateMilis;
    private List<TimeListener> listeners;

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
        paused = true;
        notifyOnTimePaused();
    }

    public void resume() {
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
