package com.moggot.mytranslator.animation;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;

import com.moggot.mytranslator.R;

/**
 * Класс анимации кнопки clear (очистка Edittext)
 */
public class ClearButtonAnimationBounce extends AnimationBounce {

    /**
     * Конструктор
     * Контекст передается в базовый класс для общей инициализации анимации
     *
     * @param context - констекс Activity
     */
    public ClearButtonAnimationBounce(Context context) {
        super(context);
    }

    /**
     * Инициализация поведения после анимации
     * Происходит очистка Edittext
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
                ((EditText) ((Activity) context).findViewById(R.id.etText)).setText("");
            }
        });
    }
}
