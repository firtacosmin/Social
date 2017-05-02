package com.social.acai.social.main;

import android.content.Context;
import android.view.View;

import com.social.acai.social.MainActivity;

import java.util.EmptyStackException;
import java.util.Stack;

import javax.inject.Inject;

/**
 * Created by firta on 4/24/2017.
 * the class that will manage the back stack of the views for the main container
 *
 */

public class ViewBackStack {

    private MainActivity mainActivity;

    private Stack<View> mTheBackStack;

    @Inject
    public ViewBackStack(Context activity){

        mainActivity = (MainActivity) activity;
        mTheBackStack = new Stack<>();


    }

    public void onActivityDestroyed(){
        mainActivity = null;
        mTheBackStack.clear();
        mTheBackStack = null;
    }


    public void push(View v){
        mTheBackStack.push(v);
    }

    public View pop(){

        try {
            return mTheBackStack.pop();
        }catch (EmptyStackException ex){
            return  null;
        }

    }


}
