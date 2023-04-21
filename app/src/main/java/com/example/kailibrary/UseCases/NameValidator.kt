package com.example.kailibrary.UseCases

import android.app.Application
import com.example.kailibrary.R
import java.util.regex.Pattern

class NameValidator {
    fun execute(name: String, app: Application): String{
        val pattern = Regex("\\w{2,20} \\w{2,20} \\w{2,20}")
        if(name.isEmpty()){
            return  app.resources.getString(R.string.fill_field)
        }else{
            if(!pattern.matches(name)){
                return app.resources.getString(R.string.wrong_fio)
            }else {
                return ""
            }
        }
    }
}