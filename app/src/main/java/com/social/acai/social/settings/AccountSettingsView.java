package com.social.acai.social.settings;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.social.acai.social.R;
import com.social.acai.social.settings.accounts.facebook.FacebookLoginCallback;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

/**
 * Created by firta on 4/24/2017.
 * The view Group that will contain the account settings
 */

public class AccountSettingsView extends FrameLayout {

    @BindView(R.id.login_button)
    LoginButton fbLoginButton;
    @BindView(R.id.txt)
    TextView t;


    boolean stop = false;



    FacebookLoginCallback mLoginCallback;
    CallbackManager mFbCallbackManager;


    @Inject
    public AccountSettingsView(Context context, FacebookLoginCallback loginCallback, CallbackManager facebooCallbackManager) {
        super(context);

        mLoginCallback = loginCallback;
        mFbCallbackManager = facebooCallbackManager;

        intiView();

    }

    private void intiView() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate( R.layout.view_account_settings, this, true);
//        addView(v);
        ButterKnife.bind(this,v);


        fbLoginButton.setReadPermissions("email");
        fbLoginButton.registerCallback(mFbCallbackManager, mLoginCallback);






    }

    @Override
    protected void onLayout(boolean changed, int l,  int top, int r, int b) {
//        intiView();
        super.onLayout(changed, l, top, r, b);
//        for ( int i = 0; i<getChildCount(); i++ ){
//            View v = getChildAt(i);
//            if ( v != null ){
//                v.layout(l, t, r, b);
//            }
//        }
    }


    @OnClick(R.id.txt)
    public void onclickTxt(View v){
        stop = true;
    }
}
