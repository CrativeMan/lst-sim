package io.crative.backend.time;

import java.time.LocalDateTime;

public class DebugTimeListener implements TimeListener {
    public DebugTimeListener() {
        TimeManager.getInstance().registerListener(this);
    }

    @Override
    public void onTimeChanged(LocalDateTime gameTime) {

    }

    @Override
    public void onTimePaused() {

    }

    @Override
    public void onTimeResumed() {

    }

    @Override
    public void onSpeedChanged(double multiplier) {

    }

    @Override
    public String getName() {
        return "DebugTimeListener";
    }
}
