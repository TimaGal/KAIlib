package com.example.kailibrary.UseCases

import android.app.Application
import com.example.kailibrary.R

//на попозже(на вечер)
class PhoneValidator {
    fun execute(phone: String, app: Application): String{
        val pattern = Regex("\\+7[ ]{1}\\([0-9]{3}\\)[ ]{1}[0-9]{3}[-]{1}[0-9]{2}[-]{1}[0-9]{2}")
        if(phone.isNotEmpty()){
            if(!pattern.matches(phone)){
                return app.resources.getString(R.string.wrong_phone)
            }else{
                return ""
            }
        }else{
            return app.resources.getString(R.string.fill_field)
        }
    }

}