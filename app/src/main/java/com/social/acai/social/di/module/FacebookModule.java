package com.social.acai.social.di.module;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.social.acai.social.settings.accounts.facebook.FacebookLoginCallback;

import dagger.Module;
import dagger.Provides;

/**
 * Created by firta on 4/28/2017.
 */

@Module
public class FacebookModule {


    @Provides
    public CallbackManager provideFacebookCallBackManager(){
        return CallbackManager.Factory.create();
    }

    @Provides
    public FacebookLoginCallback provideFacebookCallback(){
        return new FacebookLoginCallback();
    }

    @Provides
    public LoginManager provideLoginManager(CallbackManager callbackManager, FacebookLoginCallback facebookCallback){
        LoginManager lm = LoginManager.getInstance();
        lm.registerCallback(callbackManager, facebookCallback);
        return lm;
    }
    
}
