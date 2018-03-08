package com.ztt.stockinhome.main.di;

import com.ztt.stockinhome.main.ui.adapters.MainMenuListAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vtcmer on 01/08/2016.
 */
@Singleton
@Component(modules={MainMenuModule.class})
public interface MainMenuComponent {

    MainMenuListAdapter getAdapter();
}
