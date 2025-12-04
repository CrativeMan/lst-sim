package io.crative.backend.data.phonecall;

public class DebugPhoneCallListener implements PhoneCallListener {

    public int callReceived, callAccepted, callEnded, callHeld, callResumed;

    public DebugPhoneCallListener() {
        PhoneCallManager cm = PhoneCallManager.getInstance();
        cm.registerListener(this);
    }

    @Override
    public void onCallReceived(PhoneCall call) {
        System.out.println("Received call " + call);
        callReceived++;
    }

    @Override
    public void onCallAccepted(PhoneCall call) {
        System.out.println("Accepted call " + call);
        callAccepted++;
    }

    @Override
    public void onCallEnded(PhoneCall call) {
        System.out.println("Ended call " + call);
        callEnded++;
    }

    @Override
    public void onCallHeld(PhoneCall call) {
        System.out.println("Held call " + call);
        callHeld++;
    }

    @Override
    public void onCallResumed(PhoneCall call) {
        System.out.println("Resumed call " + call);
        callResumed++;
    }
}
