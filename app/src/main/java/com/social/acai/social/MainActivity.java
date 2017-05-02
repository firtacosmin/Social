package com.social.acai.social;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.social.acai.social.di.component.ActivityComponent;
import com.social.acai.social.di.component.DaggerActivityComponent;
import com.social.acai.social.di.module.ActivityContextModule;
import com.social.acai.social.di.qualifiers.MainFrameEnterAnimation;
import com.social.acai.social.di.qualifiers.MainFrameExitAnimation;
import com.social.acai.social.list.MainListView;
import com.social.acai.social.main.AccountsManager;
import com.social.acai.social.main.ViewBackStack;
import com.social.acai.social.settings.AccountSettingsView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * the container that will hold all the views
     */
    @BindView(R.id.mainContainer)
    FrameLayout mMainContainer;



    /**
     * the instance of the back stack
     */
    @Inject
    ViewBackStack mBackStack;

    /**
     * the view the will display the account where the user can log in
     */
    @Inject
    AccountSettingsView mAccountSettingsView;

    /**
     * the main view that ill display the list of items
     */
    @Inject
    MainListView mMainListView;

    @Inject
    AccountsManager mAccountsManager;

    @Inject
    CallbackManager fbCallbackManager;


    /**
     * flag set when displaying the toast to exist the app
     */
    Boolean exitingApp = false;

    @Inject
    @MainFrameEnterAnimation
    Animation enterFrameAnimation;

    @Inject
    @MainFrameExitAnimation
    Animation exitFrameAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ActivityComponent activityComponent = DaggerActivityComponent.builder().
                activityContextModule(new ActivityContextModule(this)).
                build();
        activityComponent.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mAccountsManager.configureOptionsMenu(navigationView.getMenu());
        navigationView.setNavigationItemSelectedListener(this);


        performStartActivityUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBackStack.onActivityDestroyed();
        if ( mAccountsManager != null ){
            mAccountsManager.onActivityDestroyed();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            popFromBackStack();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if ( id == R.id.account_settings ){
            goTo(mAccountSettingsView, true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * the method that will add the passed view to the {@link #mMainContainer}
     * @param v the view to be added
     * @param addToBackStack true if the view will be added to the back stack
     */
    public void goTo(View v, Boolean addToBackStack){
        View lastView = mMainContainer.getChildAt(0);
        if ( addToBackStack ){
            if ( lastView != null ) {
                mBackStack.push(lastView);
            }
        }

        if ( lastView != null ) {
            removeCurrentFrame(lastView, v);
        }else{
            addNewFrame(v);
        }


    }


    /**
     * will start the animation to remove the current frame and will add the new frame when the animation will end
     * @param lastView the view to remove
     * @param theNextView the view to add
     */
    private void removeCurrentFrame(View lastView, final View theNextView){
        exitFrameAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mMainContainer.removeViewAt(0);
                addNewFrame(theNextView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lastView.startAnimation(exitFrameAnimation);
    }

    /**
     * will start the animation to add the new view
     * @param newView the view to add
     */
    private void addNewFrame(View newView){
        enterFrameAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mMainContainer.addView(newView);
        newView.startAnimation(enterFrameAnimation);
    }






    /**
     * method that will pop the last view from the back stack and will go to it.
     * If the back stack is empty then it will try to exit the app
     */
    private void popFromBackStack() {

        View v = mBackStack.pop();
        if ( v != null ){
            goTo(v,false);
        }else{
            tryToExitApp();
        }

    }


    /**
     * method that will try to exit the application by printing a toast first
     */
    private void tryToExitApp() {

        if ( exitingApp ){
            finish();
        }else{
            exitingApp = true;
            Toast.makeText(this, R.string.exit_the_app, Toast.LENGTH_SHORT).show();
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    exitingApp = false;
                }
            }, 1000);
        }

    }

    private void performStartActivityUI() {

        goTo(mMainListView, false);

    }
}
