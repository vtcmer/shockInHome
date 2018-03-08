package com.ztt.stockinhome.main.di;

import com.ztt.stockinhome.main.entities.MainMenuItem;
import com.ztt.stockinhome.main.ui.adapters.MainMenuListAdapter;
import com.ztt.stockinhome.main.ui.adapters.OnItemClickListenerMainMenu;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtcmer on 01/08/2016.
 */
@Module
public class MainMenuModule {

    private OnItemClickListenerMainMenu onItemClickListenerMainMenu;

    public MainMenuModule(OnItemClickListenerMainMenu onItemClickListenerMainMenu) {
        this.onItemClickListenerMainMenu = onItemClickListenerMainMenu;
    }


    @Singleton
    @Provides
    OnItemClickListenerMainMenu providesOnItemClickListenerMainMenu(){
        return this.onItemClickListenerMainMenu;
    }

    @Singleton
    @Provides
    MainMenuListAdapter providesMainMenuListAdapter(final List<MainMenuItem> mainMenuItems, final OnItemClickListenerMainMenu onItemClickListenerMainMenu){
        return new MainMenuListAdapter(mainMenuItems,onItemClickListenerMainMenu);
    }

    @Singleton
    @Provides
    List<MainMenuItem> providesMainMenuItem(){
        return new ArrayList<MainMenuItem>();
    }
}
