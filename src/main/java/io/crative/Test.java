package io.crative;

import io.crative.backend.data.Location;
import io.crative.backend.data.PhoneCall;
import io.crative.backend.data.PhoneCallQueue;

public class Test {
    public static void main(String[] args) {
        PhoneCallQueue queue = new PhoneCallQueue();
        queue.add(new PhoneCall("Test 1", "Kiara", new Location()));
        queue.add(new PhoneCall("Test 2", "Kiara", new Location()));
        queue.add(new PhoneCall("Test 3", "Kiara", new Location()));

        System.out.println(queue.getNextCall().toString());
        System.out.println(queue.getNextCall().toString());
        System.out.println(queue.getNextCall().toString());
    }
}
