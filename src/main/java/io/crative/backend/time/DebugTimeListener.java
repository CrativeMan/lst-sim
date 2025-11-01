package io.crative.backend.time;

import java.time.LocalDateTime;

public class DebugTimeListener implements TimeListener{
    @Override
    public void onTimeChanged(LocalDateTime gameTime) {
        System.out.println("DebugTimeListener: Game Time changed to " + gameTime.toString());
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
        return "";
    }
}
