package com.spring.project;

public class SMSNotification implements Notification {
    @Override
    public void notifyMe() {
        System.out.println("Notify me from SMS");
    }
}
