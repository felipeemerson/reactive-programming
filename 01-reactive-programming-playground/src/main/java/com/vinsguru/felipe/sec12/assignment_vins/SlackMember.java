package com.vinsguru.felipe.sec12.assignment_vins;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class SlackMember {

    @Getter
    private final String name;

    private Consumer<String> messageConsumer;

    public SlackMember(String name) {
        this.name = name;
    }

    public void says(String message) {
        this.messageConsumer.accept(message);
    }

    void setMessageConsumer(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    void receives(String message) {
        log.info(message);
    }

}
