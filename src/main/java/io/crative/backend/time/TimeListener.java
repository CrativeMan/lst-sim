package io.crative.backend.time;

import java.time.LocalDateTime;

public interface TimeListener {
    void onTimeChanged(LocalDateTime gameTime);
    void onTimePaused();
    void onTimeResumed();
    void onSpeedChanged(double multiplier);
    String getName();
}
