package com.social.acai.social.list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.social.acai.social.R;

import javax.inject.Inject;

/**
 * Created by firta on 4/24/2017.
 */

public class MainListView extends RecyclerView {

    @Inject
    public MainListView(Context context) {
        super(context);
        setBackgroundResource(R.color.colorAccent);
    }



}
