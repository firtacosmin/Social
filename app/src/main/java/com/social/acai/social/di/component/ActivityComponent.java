package com.social.acai.social.di.component;

import com.social.acai.social.MainActivity;
import com.social.acai.social.di.module.ActivityContextModule;
import com.social.acai.social.di.module.AnimationModule;
import com.social.acai.social.di.module.FacebookModule;
import com.social.acai.social.di.scopes.ActivityScope;
import com.social.acai.social.main.ViewBackStack;
import com.social.acai.social.settings.AccountSettingsView;

import dagger.Component;

/**
 * Created by firta on 4/24/2017.
 */

@ActivityScope
@Component( modules = {ActivityContextModule.class, AnimationModule.class, FacebookModule.class})
public interface ActivityComponent {

    void inject(MainActivity act);


}
