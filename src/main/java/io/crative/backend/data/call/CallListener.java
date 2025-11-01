package io.crative.backend.data.call;

public interface CallListener {
    void onCallReceived(PhoneCall call);
    void onCallAccepted(PhoneCall call);
    void onCallEnded(PhoneCall call);
    void onCallHeld(PhoneCall call);
    void onCallResumed(PhoneCall call);
}
