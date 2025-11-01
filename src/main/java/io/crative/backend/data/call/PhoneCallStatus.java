package io.crative.backend.data.call;

import static io.crative.backend.internationalization.TranslationManager.t;

public enum PhoneCallStatus {
    RINGING,
    ACTIVE,
    ON_HOLD,
    ENDED;

    public String toString() {
        return switch (this) {
            case RINGING -> t("call.status.ringing");
            case ACTIVE -> t("call.status.active");
            case ON_HOLD -> t("call.status.on_hold");
            case ENDED -> t("call.status.ended");
        };
    }

    public String toImagePath() {
        return switch (this) {
            case RINGING -> "/icons/phone/phone-incoming.png";
            case ACTIVE -> "/icons/phone/phone-call.png";
            case ON_HOLD -> "/icons/phone/phone-hold.png";
            case ENDED -> "/icons/phone/phone-off.png";
        };
    }
}
