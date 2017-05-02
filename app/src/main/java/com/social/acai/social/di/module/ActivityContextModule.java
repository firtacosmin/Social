package com.social.acai.social.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by firta on 4/24/2017.
 */

@Module
public class ActivityContextModule {
    private final Context mContext;

    public ActivityContextModule(Context c){
        mContext = c;
    }

    @Provides
    public Context provideActivityContext(){
        return  mContext;
    }

}
