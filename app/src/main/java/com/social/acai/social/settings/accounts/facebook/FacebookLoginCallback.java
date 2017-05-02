package com.social.acai.social.settings.accounts.facebook;

import android.util.Log;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by firta on 4/28/2017.
 */

public class FacebookLoginCallback implements FacebookCallback<LoginResult> {

    public static final String TAG = "FacebookLoginCallback";

    @Override
    public void onSuccess(LoginResult loginResult) {

        Log.d(TAG,"::onSuccess");

    }

    @Override
    public void onCancel() {
        Log.d(TAG,"::onCancel");

    }

    @Override
    public void onError(FacebookException error) {
        Log.d(TAG,"::onError");
        error.printStackTrace();

    }
}
