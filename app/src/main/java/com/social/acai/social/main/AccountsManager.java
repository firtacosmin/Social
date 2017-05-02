package com.social.acai.social.main;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.social.acai.social.R;
import com.social.acai.social.settings.accounts.facebook.FacebookManager;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by firta on 5/1/2017.
 * A class that will manage all the accounts on the app
 */


public class AccountsManager {
    public static final String TAG = "AccountsManager";

    private FacebookManager mFbManager;
    private Subscription mFbSubscription;

    private Menu mOptionsMenu;

    private MenuItem mFbMenu;



    private Subscriber<FacebookManager> mFbSubscriber = new Subscriber<FacebookManager>() {
        @Override
        public void onCompleted() {

            Log.d(TAG,"::onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG,"::onError");
        }

        @Override
        public void onNext(FacebookManager facebookManager) {
            Log.d(TAG,"::onNext");
            reconfigureMenu();
        }
    };


    @Inject
    public AccountsManager(FacebookManager fbManager){
        mFbManager = fbManager;
        mFbSubscription = mFbManager.getSubject()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(mFbSubscriber);
    }

    public void configureOptionsMenu(Menu menu)
    {


        mOptionsMenu = menu;
        getMenuItems();
        updateMenuItems();
    }

    private void updateMenuItems() {

        if ( mFbMenu != null)
        {
            if ( mFbManager == null ||  !mFbManager.amILoggedIn() )
            {
                mFbMenu.setVisible(false);

            }else
            {
                mFbMenu.setVisible(true);

            }
        }


    }

    private void getMenuItems() {

        if ( mOptionsMenu != null ){
            mFbMenu = mOptionsMenu.findItem(R.id.fb_menu);
        }

    }

    public void reconfigureMenu(){
        if ( mOptionsMenu != null ){
            updateMenuItems();
        }
    }


    public void onActivityDestroyed(){
        if ( mFbSubscription != null ){
            mFbSubscription.unsubscribe();
        }
    }




}
