package io.crative.event;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class EventBus {
    private static EventBus instance;
    private final Map<Class<?>, List<Consumer<?>>> subscribers = new ConcurrentHashMap<>();

    private EventBus() {}

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public <T> void subscribe(Class<T> eventType, Consumer<T> handler) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }

    public <T> void unsubscribe(Class<T> eventType, Consumer<T> handler) {
        System.out.println("EventBus: Total subscribers before unsubscription: " + getTotalSubscriberCount());
        List<Consumer<?>> handlers = subscribers.get(eventType);
        if (handlers != null) {
            System.out.println("EventBus: Unsubscribing handler from event type " + eventType.getSimpleName());
            handlers.remove(handler);
        }
        System.out.println("EventBus: Unsubscribed handler from event type " + eventType.getSimpleName());
        System.out.println("EventBus: Total subscribers after unsubscription: " + getTotalSubscriberCount());
    }

    public <T> void post(T event) {
        Class<?> eventType = event.getClass();
        List<Consumer<?>> handlers = subscribers.get(eventType);

        if (handlers != null) {
            for (Consumer<?> handler : handlers) {
               try {
                   @SuppressWarnings("unchecked")
                   Consumer<T> typeHandler = (Consumer<T>) handler;
                   typeHandler.accept(event);
               } catch (Exception e) {
                   System.err.println("EventBus: Error while handling event of type " + eventType.getSimpleName() + ": " + e.getMessage());
                   e.printStackTrace();
               }
            }
        }
    }

    public <T> void postOnUIThread(T event) {
        javafx.application.Platform.runLater(() -> post(event));
    }

    public int getTotalSubscriberCount() {
        return subscribers.values().stream().mapToInt(List::size).sum();
    }
}
