package com.moggot.mytranslator.animation;

import android.view.animation.Interpolator;

/**
 * Класс физики анимации затухания
 */

//
// Interpolator to be used with Android view AddAlarmAnimationBounce class to achieve the spring-bounce effect.
//
// License: MIT
// Source: http://evgenii.com/blog/spring-button-animation-on-android/
//
// Usage example, make the button wobble in scale:
// ------------
//
//    // Load animation
//    final AddAlarmAnimationBounce myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
//    double animationDuration = getDurationValue() * 1000;
//
//    // Create interpolator with the amplitude 0.2 and frequency 20
//    MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
//
//    myAnim.setInterpolator(interpolator);
//    Button button = (Button)findViewById(R.id.button_to_animate);
//    button.initAnimationBehavior(myAnim);
//
// anim/bounce.xml file:
// --------------------
//
//    <?xml version="1.0" encoding="utf-8"?>
//    <set xmlns:android="http://schemas.android.com/apk/res/android" >
//
//    <scale
//    android:duration="2000"
//            android:fromXScale="0.3"
//            android:toXScale="1.0"
//            android:fromYScale="0.3"
//            android:toYScale="1.0"
//            android:pivotX="50%"
//            android:pivotY="50%" />
//    </set>
//
//
public class MyBounceInterpolator implements Interpolator {

    /**
     * Амплитуда колебаний
     */
    private double mAmplitude = 1;

    /**
     * Частота колебаний
     */
    private double mFrequency = 10;

    /**
     * Инициализация нового интерполятора
     *
     * @param amplitude - амплитуда интерполятора
     * @param frequency - частота интерполятора
     */
    public MyBounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    /**
     * Получение интерполятора
     *
     * @param time - время анимации
     * @return интерполятора
     */
    public float getInterpolation(float time) {
        // The interpolation curve equation:
        //    -e^(-time / amplitude) * cos(frequency * time) + 1
        //
        // View the graph live: https://www.desmos.com/calculator/6gbvrm5i0s
        return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) * Math.cos(mFrequency * time) + 1);
    }
}
