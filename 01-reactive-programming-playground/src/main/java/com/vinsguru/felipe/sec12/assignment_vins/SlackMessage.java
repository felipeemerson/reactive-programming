package com.vinsguru.felipe.sec12.assignment_vins;

public record SlackMessage(String sender,
                           String message) {

    private static final String MESSAGE_FORMAT = "[%s -> %s]: %s";

    public String formatForDelivery(String receiver) {
        return MESSAGE_FORMAT.formatted(sender, receiver, message);
    }

}
