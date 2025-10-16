package io.crative.backend;

import io.crative.backend.data.Location;
import io.crative.backend.data.PhoneCall;
import io.crative.backend.data.PhoneCallStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CallManager {
    private final List<PhoneCall> callQueue;
    private final List<CallListener> listeners;
    private PhoneCall activeCall;

    private static final CallManager instance = new CallManager();

    private CallManager() {
        callQueue = new ArrayList<>();
        listeners = new ArrayList<>();
        activeCall = null;
    }

    public static CallManager getInstance() {
        return instance;
    }

    // =================================================================================================================
    // Call Operations
    public void receiveIncomingCall(String number, String callerName, Location location) {
        PhoneCall call = new PhoneCall(number, callerName, location);
        callQueue.add(call);
        notifyCallReceived(call);
    }

    public void acceptCall(PhoneCall call) {
        if (activeCall != null) {
            System.err.println("CallManager: Already one active call");
            return;
        }
        call.accept();
        activeCall = call;
        notifyCallAccepted(call);
    }

    public void endCall(PhoneCall call) {
        if (activeCall == null) {
            System.err.println("CallManager: No active call to end");
            return;
        }
        call.end();
        activeCall = null;
        callQueue.remove(call);
        notifyCallEnded(call);
    }

    public void holdCall(PhoneCall call) {
        if (activeCall == null) {
            System.err.println("CallManager: No active call to hold");
            return;
        }
        call.hold();
        activeCall = null;
        notifyCallHeld(call);
    }

    public void resumeCall(PhoneCall call) {
        if (activeCall == null) {
            System.err.println("CallManager: No active call to resume");
            return;
        }
        call.accept();
        activeCall = call;
        notifyCallResumed(call);
    }

    // =================================================================================================================
    // Listeners
    public void registerListener(CallListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(CallListener listener) {
        listeners.remove(listener);
    }

    public void notifyCallReceived(PhoneCall call) {
        for (CallListener l : new ArrayList<>(listeners)) {
            l.onCallReceived(call);
        }
    }

    public void notifyCallAccepted(PhoneCall call) {
        for (CallListener l : new ArrayList<>(listeners)) {
            l.onCallAccepted(call);
        }
    }

    public void notifyCallEnded(PhoneCall call) {
        for (CallListener l : new ArrayList<>(listeners)) {
            l.onCallEnded(call);
        }
    }

    public void notifyCallHeld(PhoneCall call) {
        for (CallListener l : new ArrayList<>(listeners)) {
            l.onCallHeld(call);
        }
    }

    public void notifyCallResumed(PhoneCall call) {
        for (CallListener l : new ArrayList<>(listeners)) {
            l.onCallResumed(call);
        }
    }

    // =================================================================================================================
    // Query

    public PhoneCall getActiveCall() {
        return activeCall;
    }

    public List<PhoneCall> getIncomingCalls() {
        List<PhoneCall> incoming = new ArrayList<>();
        for (PhoneCall call : callQueue) {
            if (call.getStatus() == PhoneCallStatus.RINGING)
                incoming.add(call);
        }
        return incoming;
    }

    public List<PhoneCall> getAllCalls() {
        return callQueue;
    }

    public PhoneCall getCallByUUID(UUID uuid) {
        for (PhoneCall call : callQueue) {
            if (call.getUuid().equals(uuid))
                return call;
        }
        return null;
    }
}
