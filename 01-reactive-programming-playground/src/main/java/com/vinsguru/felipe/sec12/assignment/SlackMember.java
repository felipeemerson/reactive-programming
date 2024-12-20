package com.vinsguru.felipe.sec12.assignment;

import com.vinsguru.common.Util;

public class SlackMember {

    private final String name;
    private SlackRoom room;

    public SlackMember(String name) {
        this.name = name;
    }

    public void says(String message) {
        this.room.sendMessage(name, message);
    }

    public void setRoom(SlackRoom room) {
        this.room = room;
        this.room.getSink()
                .asFlux()
                .subscribe(Util.subscriber(name));
    }
}
