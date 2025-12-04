package io.crative.backend;

import io.crative.backend.data.phonecall.PhoneCallManager;
import io.crative.backend.data.phonecall.Location;
import io.crative.backend.data.phonecall.PhoneCall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CallManagerTest {

    private PhoneCallManager callManager;

    @BeforeEach
    void setUp() {
        callManager = PhoneCallManager.getInstance();
        // Clear any existing calls for isolation (if needed, add a clear method to CallManager)
    }

    @Test
    void testReceiveIncomingCallAddsCall() {
        int initialSize = callManager.getAllCalls().size();
        callManager.receiveIncomingCall("12345", "Alice", new Location());
        assertEquals(initialSize + 1, callManager.getAllCalls().size());
    }

    @Test
    void testAcceptCallSetsActiveCall() {
        callManager.receiveIncomingCall("12345", "Bob", new Location());
        PhoneCall call = callManager.getAllCalls().get(0);
        callManager.acceptCall(call);
        assertEquals(call, callManager.getActiveCall());
    }

    @Test
    void testEndCallRemovesActiveCall() {
        callManager.receiveIncomingCall("12345", "Charlie", new Location());
        PhoneCall call = callManager.getAllCalls().get(0);
        callManager.acceptCall(call);
        callManager.endCall(call);
        assertNull(callManager.getActiveCall());
        assertFalse(callManager.getAllCalls().contains(call));
    }
}
