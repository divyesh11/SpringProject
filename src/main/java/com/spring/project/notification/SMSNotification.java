package com.spring.project.notification;

import com.spring.project.notification.interfaces.Notification;

public class SMSNotification implements Notification {
    @Override
    public void notifyMe() {
        System.out.println("Notify me from SMS");
    }
}
