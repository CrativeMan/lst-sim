package io.crative.backend;

import io.crative.backend.data.PhoneCall;

public interface CallListener {
    void onCallReceived(PhoneCall call);
    void onCallAccepted(PhoneCall call);
    void onCallEnded(PhoneCall call);
    void onCallHeld(PhoneCall call);
    void onCallResumed(PhoneCall call);
}
