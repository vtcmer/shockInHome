package com.ztt.stockinhome.libs.impl;


import com.ztt.stockinhome.libs.EventBus;

/**
 * Created by vtcmer on 11/07/2016.
 */
public class GreenRobotEventBus implements EventBus {

    private org.greenrobot.eventbus.EventBus eventBus;


    public GreenRobotEventBus(org.greenrobot.eventbus.EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void register(Object subscriber) {
        this.eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        this.eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        this.eventBus.post(event);
    }
}
