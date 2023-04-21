package com.example.kailibrary

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import com.example.kailibrary.databinding.ActivityWelcomeScreenBinding

class Welcome_screen : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnReg.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
        binding.btnHaveAcc.setOnClickListener {
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
        }

        //object animator
        //ObjectAnimator.ofFloat(binding.kaiView, View.ROTATION_X, 0f, 720f).apply {
        //    duration = 1000
        //    interpolator = DecelerateInterpolator()
        //    start()
        //}

        //несколько анимаций одновременно
        //val rotationX = PropertyValuesHolder.ofFloat(View.ROTATION_X, 0f, 720f)
//
        //val moveY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, -200f)
//
        //val animation = ObjectAnimator.ofPropertyValuesHolder(binding.kaiView, rotationX, moveY).apply {
        //    duration = 1000
        //    interpolator = DecelerateInterpolator()
        //}
//
        //val anim1 = ObjectAnimator.ofPropertyValuesHolder(binding.kaiView, rotationX).apply {
        //    duration = 2000
        //    interpolator = DecelerateInterpolator()
        //}
//
        //val anim2 = ObjectAnimator.ofPropertyValuesHolder(binding.kaiView, moveY).apply {
        //    duration = 1000
        //    interpolator = DecelerateInterpolator()
        //    startDelay = 1000
        //}
//
        //AnimatorSet().apply {
        //    play(anim1).with(anim2)
        //    start()
        //}

        val rotationX = PropertyValuesHolder.ofFloat(View.ROTATION_X, -45f, 0f)
        val moveY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 300f, 0f)
        val opacity = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)
        val visibleAnimLogo = ObjectAnimator.ofPropertyValuesHolder(binding.kaiView, opacity).apply {
            startDelay = 450
            duration = 600
            interpolator = AccelerateInterpolator()
        }

        val rotationX1 = PropertyValuesHolder.ofFloat(View.ROTATION_X, -30f, 0f)
        val moveY1 = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 150f, 0f)
        val rotationAnimWelcome = ObjectAnimator.ofPropertyValuesHolder(binding.welcome, rotationX1, moveY1, opacity).apply {
            startDelay = 100
            duration = 200
            interpolator = AccelerateInterpolator()
        }

        val rotationX2 = PropertyValuesHolder.ofFloat(View.ROTATION_X, -15f, 0f)
        val moveY2 = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 100f, 0f)
        val rotationAnimBtnReg = ObjectAnimator.ofPropertyValuesHolder(binding.btnReg, rotationX2, moveY2, opacity).apply {
            startDelay = 200
            duration = 300
            interpolator = AccelerateInterpolator()
        }

        val rotationX3 = PropertyValuesHolder.ofFloat(View.ROTATION_X, -15f, 0f)
        val moveY3 = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 50f, 0f)
        val rotationAnimBtnHaveAcc = ObjectAnimator.ofPropertyValuesHolder(binding.btnHaveAcc, rotationX3, moveY3, opacity).apply {
            startDelay = 300
            duration = 400
            interpolator = AccelerateInterpolator()
        }


        AnimatorSet().apply {
            play(rotationAnimWelcome).with(rotationAnimBtnReg).with(rotationAnimBtnHaveAcc).with(visibleAnimLogo)
            start()
        }
    }
}