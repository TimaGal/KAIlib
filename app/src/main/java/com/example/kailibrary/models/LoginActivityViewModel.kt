package com.example.kailibrary.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kailibrary.RegFormState
import com.example.kailibrary.UseCases.*
import com.example.kailibrary.loginActivity
import javax.inject.Inject

class LoginActivityViewModel(val app: Application): AndroidViewModel(app) {

    var regFormState: RegFormState

    var emailErrorState: MutableLiveData<String>
    var phoneErrorState: MutableLiveData<String>
    var nameErrorState: MutableLiveData<String>
    var passErrorState: MutableLiveData<ArrayList<Boolean>>
    var pass2ErrorState: MutableLiveData<String>
    var stateMap = mutableMapOf<String, Boolean>("Email_phone" to false, "name" to false, "pass" to false, "pass2" to false)
    var stateData: MutableLiveData<Map<String, Boolean>>

    var switcherState: MutableLiveData<Int>
    init {
        regFormState = RegFormState(EmailValidator(), PhoneValidator(), NameValidator(), PassValidator(), Pass2Validator())
        switcherState = MutableLiveData()
        emailErrorState = MutableLiveData()
        phoneErrorState = MutableLiveData()
        nameErrorState = MutableLiveData()
        passErrorState = MutableLiveData()
        pass2ErrorState = MutableLiveData()
        stateData = MutableLiveData()
        switcherState.postValue(0)
    }

    fun validateEmail(email: String) {
        val res = regFormState.emailValidator.execute(email, app)
        emailErrorState.postValue(res)
        stateMap["Email_phone"] = if (res.isEmpty()) true else false
        stateData.postValue(stateMap)
    }
    fun validatePhone(phone: String){
        val res = regFormState.phoneValidator.execute(phone, app)
        phoneErrorState.postValue(res)
        stateMap["Email_phone"] = if(res.isEmpty()) true else false
        stateData.postValue(stateMap)
    }

    fun validateName(name: String){
        val res = regFormState.nameValidator.execute(name, app)
        nameErrorState.postValue(res)
        stateMap["name"] = if(res.isEmpty()) true else false
        stateData.postValue(stateMap)
    }

    fun validatePass(pass: String){
        val res = regFormState.passValidator.execute(pass)
        passErrorState.postValue(res)
        val corrects = res.count { it == true }
        stateMap["pass"] = if(corrects == 4) true else false
        stateData.postValue(stateMap)
    }

    fun validatePass2(pass: String, pass2: String){
        val res = regFormState.pass2Validator.execute(pass, pass2, app)
        pass2ErrorState.postValue(res)
        stateMap["pass2"] = if(res.isEmpty()) true else false
        stateData.postValue(stateMap)
    }

}