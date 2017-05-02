package com.social.acai.social.settings.accounts.facebook;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

import javax.inject.Inject;

import rx.subjects.PublishSubject;

/**
 * Created by firta on 4/28/2017.
 * the class that will be managing the facebook activity
 */


public class FacebookManager {

    private final AccessTokenTracker accessTokenTracker;
    private AccessToken mFbAccessToken;

    private PublishSubject<FacebookManager> theSubject;


    @Inject
    public FacebookManager(){
        theSubject = PublishSubject.create();



        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
                mFbAccessToken = currentAccessToken;
                theSubject.onNext(FacebookManager.this);

            }
        };

        /*
         * will try to get the current access token
         */
        mFbAccessToken = AccessToken.getCurrentAccessToken();
    }


    public void setAccessToken(AccessToken token){
        mFbAccessToken = token;
    }

    public boolean amILoggedIn() {

        return mFbAccessToken != null && !mFbAccessToken.isExpired();


    }


    public PublishSubject<FacebookManager> getSubject(){
        return theSubject;
    }

}
