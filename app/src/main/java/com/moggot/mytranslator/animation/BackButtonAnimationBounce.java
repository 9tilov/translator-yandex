package com.moggot.mytranslator.animation;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by toor on 10.04.17.
 */

public class BackButtonAnimationBounce extends AnimationBounce {

    public BackButtonAnimationBounce(Context context) {
        super(context);
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
                ((Activity) context).finish();
            }
        });
    }
}
