package io.crative.backend;

import io.crative.backend.data.Location;
import io.crative.backend.data.PhoneCall;
import io.crative.backend.data.PhoneCallStatus;

import java.util.ArrayList;
import java.util.List;

public class CallManager {
    private static List<PhoneCall> callQueue;
    private static List<CallListener> listeners;
    private static PhoneCall activeCall;

    private static CallManager instance;

    private CallManager() {
        callQueue = new ArrayList<>();
        listeners = new ArrayList<>();
        activeCall = null;
    }

    public static CallManager getInstance() {
        if (instance == null) {
            instance = new CallManager();
        }
        return instance;
    }

    // =================================================================================================================
    // Call Operations
    public static void receiveIncomingCall(String number, String callerName,Location location) {
        PhoneCall call = new PhoneCall(number, callerName,location);
        callQueue.add(call);
        notifyCallReceived(call);
    }

    public static void acceptCall(PhoneCall call) {
        if (activeCall != null) {
            System.err.println("CallManager: Already one active call");
            return;
        }
        call.accept();
        activeCall = call;
        notifyCallAccepted(call);
    }

    public static void endCall(PhoneCall call) {
        if (activeCall == null) {
            System.err.println("CallManager: No active call to end");
            return;
        }
        call.end();
        activeCall = null;
        notifyCallEnded(call);
    }

    public static void holdCall(PhoneCall call) {
        if (activeCall == null) {
            System.err.println("CallManager: No active call to hold");
            return;
        }
        call.hold();
        notifyCallHeld(call);
    }

    public static void resumeCall(PhoneCall call) {
        if (activeCall == null) {
            System.err.println("CallManager: No active call to resume");
            return;
        }
        call.accept();
        notifyCallResumed(call);
    }

    // =================================================================================================================
    // Listeners
    public static void registerListener(CallListener listener) {
        listeners.add(listener);
    }

    public static void unregisterListener(CallListener listener) {
        listeners.remove(listener);
    }

    public static void notifyCallReceived(PhoneCall call) {
        for (CallListener l : listeners) {
            l.onCallReceived(call);
        }
    }

    public static void notifyCallAccepted(PhoneCall call) {
        for (CallListener l : listeners) {
            l.onCallAccepted(call);
        }
    }

    public static void notifyCallEnded(PhoneCall call) {
        for (CallListener l : listeners) {
            l.onCallEnded(call);
        }
    }

    public static void notifyCallHeld(PhoneCall call) {
        for (CallListener l : listeners) {
            l.onCallHeld(call);
        }
    }

    public static void notifyCallResumed(PhoneCall call) {
        for (CallListener l : listeners) {
            l.onCallResumed(call);
        }
    }

    // =================================================================================================================
    // Query

    public static PhoneCall getActiveCall() {
        return activeCall;
    }

    public static List<PhoneCall> getIncomingCalls() {
        List<PhoneCall> incoming = new ArrayList<>();
        for (PhoneCall call : callQueue) {
            if (call.getStatus() == PhoneCallStatus.RINGING)
                incoming.add(call);
        }
        return incoming;
    }

    public static List<PhoneCall> getAllCalls() {
        return callQueue;
    }
}
