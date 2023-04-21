package com.example.kailibrary.UseCases

import android.app.Application
import com.example.kailibrary.MyApp
import com.example.kailibrary.R

class EmailValidator {
    fun execute(email: String, app: Application): String{
        val pattern = Regex("[A-Za-z0-9.]{5,30}@(mail\\.ru|gmail\\.com|yandex\\.ru|inbox\\.ru|bk\\.ru)")
        if(email.isNotEmpty()){
            if(!pattern.matches(email)){
                return app.resources.getString(R.string.wrong_email)
            }else{
                return ""
            }
        }
        else{
            return app.resources.getString(R.string.fill_field)
        }
    }
}