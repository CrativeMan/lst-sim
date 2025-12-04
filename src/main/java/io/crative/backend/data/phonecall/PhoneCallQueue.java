package io.crative.backend.data.phonecall;

import java.util.Stack;

/*
List of all Phone calls
Get reference to the first one | to a specific one
Set that one to active
Put calls on hold
 */

public class PhoneCallQueue {
    private Stack<PhoneCall> queue;

    public PhoneCallQueue() {
        queue = new Stack<>();
    }

    public void add(PhoneCall call) {
        if(!queue.contains(call)) {
            queue.add(call);
        } else {
            System.out.println("PhoneCallQueue: Call already exists in queue");
        }
    }

    public PhoneCall getNextCall() {
        return null;
    }

    public PhoneCall getCall() {
        return null;
    }

    public void removeCall() {

    }
}
