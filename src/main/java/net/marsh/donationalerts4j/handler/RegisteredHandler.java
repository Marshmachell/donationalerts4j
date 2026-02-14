package net.marsh.donationalerts4j.handler;

import net.marsh.donationalerts4j.enums.EventPriority;
import net.marsh.donationalerts4j.listener.AlertListener;

import java.lang.reflect.Method;

public record RegisteredHandler(AlertListener listener, Method method, EventPriority priority, boolean ignored) {
}
