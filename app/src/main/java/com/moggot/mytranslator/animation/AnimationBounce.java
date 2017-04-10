package com.moggot.mytranslator.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.moggot.mytranslator.R;
import com.moggot.mytranslator.TranslatorContext;

/**
 * Created by toor on 15.03.17.
 */

public abstract class AnimationBounce {

    protected TranslatorContext translatorContext;
    protected final Animation animation;

    public AnimationBounce(TranslatorContext translatorContext) {
        this.translatorContext = translatorContext;
        this.animation = AnimationUtils.loadAnimation(translatorContext.getContext(), R.anim.bounce);
        double animationDuration = 0.5 * 1000;
        animation.setDuration((long) animationDuration);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.50, 15);

        animation.setInterpolator(interpolator);
    }

    private void initButton(View view) {
        view.startAnimation(animation);
    }

    protected abstract void startAnimation(View view);

    public final void animate(final View view) {
        initButton(view);
        startAnimation(view);
    }

}
