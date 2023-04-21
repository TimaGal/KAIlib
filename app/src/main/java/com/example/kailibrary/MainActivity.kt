package com.example.kailibrary

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.kailibrary.databinding.ActivityMainBinding
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.utils.VKUtils.getCertificateFingerprint


class MainActivity : AppCompatActivity() {
    var switchNum = 0;

    lateinit var imageSlider: ImageSlider
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fingerprints = getCertificateFingerprint(this, this.packageName)
        Log.e("fingerprint","${fingerprints?.get(0)}")
        imageSlider = findViewById(R.id.imgSlider)
        val imgList = ArrayList<SlideModel>();
        imgList.add(SlideModel(R.drawable.lib1, "First", ScaleTypes.CENTER_CROP))
        imgList.add(SlideModel(R.drawable.lib2, "Second", ScaleTypes.CENTER_CROP))
        imgList.add(SlideModel(R.drawable.article, "Third", ScaleTypes.CENTER_CROP))
        imageSlider.setImageList(imgList)

        binding.apply {
            img.setOnClickListener {
                if(switchNum == 0){
                    img.setImageResource(R.drawable.avd_play_to_pause)
                    val avdPlayToPause = img.drawable as AnimatedVectorDrawable
                    avdPlayToPause.start()
                    switchNum = 1

                }else{
                    img.setImageResource(R.drawable.avd_pause_to_play)
                    val avdPauseToPlay = img.drawable as AnimatedVectorDrawable
                    avdPauseToPlay.start()
                    switchNum = 0
                }
            }

            img2.setOnClickListener {
                img2.setImageResource(R.drawable.avd_anim_buy)
                val avd = img2.drawable as AnimatedVectorDrawable
                avd.start()
            }

            btn.setOnClickListener {
                VK.login(this@MainActivity, arrayListOf(VKScope.WALL, VKScope.PHOTOS))
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Toast.makeText(applicationContext, "Succesful", Toast.LENGTH_SHORT).show()
                //пользователь успешно авторизовалсяin
            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}