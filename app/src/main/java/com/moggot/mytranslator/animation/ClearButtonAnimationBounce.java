package com.moggot.mytranslator.animation;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;

import com.moggot.mytranslator.R;

/**
 * Created by toor on 10.04.17.
 */

public class ClearButtonAnimationBounce extends AnimationBounce {

    public ClearButtonAnimationBounce(Context context) {
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
                ((EditText)((Activity)context).findViewById(R.id.etText)).setText("");
            }
        });
    }
}
