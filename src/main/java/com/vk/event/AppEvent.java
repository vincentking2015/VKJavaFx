package com.vk.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * @author VINCE
 */
public abstract class AppEvent extends Event {
    public AppEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public AppEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
