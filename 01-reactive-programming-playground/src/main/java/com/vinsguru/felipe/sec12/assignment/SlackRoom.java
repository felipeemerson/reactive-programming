package com.vinsguru.felipe.sec12.assignment;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Sinks;

@Slf4j
public class SlackRoom {

    private final String name;

    @Getter
    private final Sinks.Many<String> sink;

    public SlackRoom(String name) {
        this.name = name;
        this.sink = Sinks.many().replay().all();
        log.info("initializing room {}", name);
    }

    public void addMember(SlackMember member) {
        member.setRoom(this);
    }

    public void sendMessage(String person, String message) {
        sink.tryEmitNext(person + ": " + message);
    }

}
