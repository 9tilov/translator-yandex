package com.moggot.mytranslator.animation;

import android.view.View;
import android.view.animation.Animation;

import com.moggot.mytranslator.TranslatorContext;

/**
 * Created by toor on 10.04.17.
 */

public class ClickButtonAnimationBounce extends AnimationBounce {

    public ClickButtonAnimationBounce(TranslatorContext translatorContext) {
        super(translatorContext);
    }

    @Override
    protected void startAnimation(final View view) {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation arg0) {

            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {

            }
        });
    }
}
