package com.social.acai.social.di.module;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.social.acai.social.R;
import com.social.acai.social.di.qualifiers.MainFrameEnterAnimation;
import com.social.acai.social.di.qualifiers.MainFrameExitAnimation;

import dagger.Module;
import dagger.Provides;

/**
 * Created by firta on 4/28/2017.
 */

@Module
public class AnimationModule {

    @Provides
    @MainFrameEnterAnimation
    public Animation provideEnterAnimation(Context c){
        return AnimationUtils.loadAnimation(c.getApplicationContext(), R.anim.main_frame_enter);
    }

    @Provides
    @MainFrameExitAnimation
    public Animation provideExitAnimation(Context c){
        return AnimationUtils.loadAnimation(c.getApplicationContext(), R.anim.main_frame_exit);
    }


}
