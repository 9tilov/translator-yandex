package com.moggot.multipreter.animation;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

/**
 * Класс анимации кнопки back в Activity с выбором языка
 */
public class BackButtonAnimationBounce extends AnimationBounce {

    /**
     * Конструктор
     * Контекст передается в базовый класс для общей инициализации анимации
     *
     * @param context - констекс Activity
     */
    public BackButtonAnimationBounce(Context context) {
        super(context);
    }

    /**
     * Инициализация поведения после анимации
     * Происходит переход в предыдущую Activity
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
                ((Activity) context).finish();
            }
        });
    }
}
