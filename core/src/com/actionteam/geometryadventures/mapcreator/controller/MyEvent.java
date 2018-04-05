package com.actionteam.geometryadventures.mapcreator.controller;

/**
 * Created by theartful on 4/4/18.
 */

class MyEvent {
    int eventCode;
    Object message;

    public MyEvent(int eventCode, Object message) {
        this.eventCode = eventCode;
        this.message = message;
    }
}
