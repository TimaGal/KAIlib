package com.example.kailibrary.UseCases

import android.app.Application
import com.example.kailibrary.R

class Pass2Validator {
    fun execute(pass1: String, pass2: String, app: Application): String{
        if(!pass1.equals(pass2)){
            return app.resources.getString(R.string.pass_eq)
        }else{
            return ""
        }
    }
}