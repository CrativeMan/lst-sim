package io.crative.backend.data.phonecall;

public interface PhoneCallListener {
    void onCallReceived(PhoneCall call);
    void onCallAccepted(PhoneCall call);
    void onCallEnded(PhoneCall call);
    void onCallHeld(PhoneCall call);
    void onCallResumed(PhoneCall call);
}
