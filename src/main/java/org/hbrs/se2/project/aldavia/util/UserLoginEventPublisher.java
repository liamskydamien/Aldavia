package org.hbrs.se2.project.aldavia.util;

import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UserLoginEventPublisher {

    private static UserLoginEventPublisher instance;
    private final List<Consumer<UserDTO>> listeners = new ArrayList<>();

    private UserLoginEventPublisher() {
    }

    public static UserLoginEventPublisher getInstance() {
        if (instance == null) {
            instance = new UserLoginEventPublisher();
        }
        return instance;
    }

    public void addListener(Consumer<UserDTO> listener) {
        listeners.add(listener);
    }

    public void removeListener(Consumer<UserDTO> listener) {
        listeners.remove(listener);
    }

    public void publish(UserDTO user) {
        for (Consumer<UserDTO> listener : listeners) {
            listener.accept(user);
        }
    }
}

