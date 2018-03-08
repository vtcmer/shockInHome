package com.ztt.stockinhome.libs.di;

import android.app.Activity;

import com.ztt.stockinhome.libs.EventBus;
import com.ztt.stockinhome.libs.impl.GreenRobotEventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtcmer on 27/07/2016.
 */
@Module
public class LibsModule {

    private Activity context;

    public LibsModule() {
    }

    public LibsModule(Activity context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Activity providesActivity(){
        return this.context;
    }


    @Singleton
    @Provides
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus){
        return new GreenRobotEventBus(eventBus);
    }

    @Singleton
    @Provides
    org.greenrobot.eventbus.EventBus providesDefaultEventBus(){
        return org.greenrobot.eventbus.EventBus.getDefault();
    }
}
