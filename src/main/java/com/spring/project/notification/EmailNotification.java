package com.spring.project.notification;

import com.spring.project.notification.interfaces.Notification;

public class EmailNotification implements Notification {

    private final String email;

    public EmailNotification(String _email) {
        email = _email;
    }

    @Override
    public void notifyMe() {
        System.out.printf("Notify me from Email %S", email);
    }
}
