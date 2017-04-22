package com.moggot.mytranslator.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

/**
 * Класс пустой анимации, после которой не происходит никаких действий
 */
public class EmptyAnimationBounce extends AnimationBounce {

    /**
     * Конструктор
     * Контекст передается в базовый класс для общей инициализации анимации
     *
     * @param context - констекс Activity
     */
    public EmptyAnimationBounce(Context context) {
        super(context);
    }

    /**
     * Инициализация поведения после анимации
     *
     * @param view - view, которую необходимо анимировать
     */
    @Override
    protected void initAnimationBehavior(final View view) {
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