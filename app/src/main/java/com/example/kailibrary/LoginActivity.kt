package com.example.kailibrary

import android.annotation.SuppressLint
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.kailibrary.databinding.ActivityLoginBinding
import com.example.kailibrary.di.App1Component
import com.example.kailibrary.di.AppComponent
import com.example.kailibrary.di.DaggerApp1Component
import com.example.kailibrary.di.DaggerAppComponent
import com.example.kailibrary.models.LoginActivityViewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.PhoneNumberUnderscoreSlotsParser
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var loginActivityViewModel: LoginActivityViewModel

    lateinit var binding: ActivityLoginBinding

    lateinit var appComponent: AppComponent

    lateinit var phoneEditText: EditText

    var formatWatcher: FormatWatcher? = null

    //сюда нам нужен instance у ViewModel
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appComponent = DaggerAppComponent.builder().loginActivity(this).build()
        appComponent.inject(this)

        phoneEditText = findViewById(R.id.email_phone)
        binding.viaPhone.setOnClickListener {
            binding.emailPhoneLayout.error = null
            loginActivityViewModel.switcherState.postValue(1)
        }
        binding.viaEmail.setOnClickListener {
            binding.emailPhoneLayout.error = null
            loginActivityViewModel.switcherState.postValue(0)
        }
        binding.btnReg.isEnabled = false


        loginActivityViewModel.switcherState.observe(this, {
            if(it == 1){
                binding.viaEmailL.setBackgroundColor(Color.WHITE)
                binding.viaEmailBtn.setTextColor(Color.BLACK)
                binding.viaPhoneL.setBackgroundColor(Color.parseColor("#3F51B5"))
                binding.viaPhoneBtn.setTextColor(Color.WHITE)
                binding.emailPhone.inputType = InputType.TYPE_CLASS_PHONE
                binding.emailPhoneLayout.hint = "Номер телефона"
                binding.emailPhoneLayout.setStartIconDrawable(R.drawable.ic_phone)
                binding.emailPhone.setText("")

                createFormatWatcher()
                createMask()

                binding.emailPhone.doOnTextChanged { text, start, before, count ->
                    validatePhone(text.toString())
                }
            }else{
                formatWatcher?.removeFromTextView()

                binding.viaPhoneL.setBackgroundColor(Color.WHITE)
                binding.viaPhoneBtn.setTextColor(Color.BLACK)
                binding.viaEmailL.setBackgroundColor(Color.parseColor("#3F51B5"))
                binding.viaEmailBtn.setTextColor(Color.WHITE)
                binding.emailPhone.inputType = InputType.TYPE_CLASS_TEXT
                binding.emailPhoneLayout.hint = "E-mail"
                binding.emailPhoneLayout.setStartIconDrawable(R.drawable.ic_email)
                binding.emailPhone.setText("")
                binding.emailPhone.doOnTextChanged { text, start, before, count ->
                    validateEmail(text.toString())
                }
            }
        })
        loginActivityViewModel.emailErrorState.observe(this, {
            if(binding.emailPhone.inputType == InputType.TYPE_CLASS_TEXT){
                if(it.isEmpty()){
                    binding.emailPhoneLayout.error = null
                }else{
                    binding.emailPhoneLayout.error = it
                }
            }
        })
        loginActivityViewModel.phoneErrorState.observe(this, {
            if(binding.emailPhone.inputType == InputType.TYPE_CLASS_PHONE){
                if(it.isEmpty()){
                    binding.emailPhoneLayout.error = null
                }else{
                    binding.emailPhoneLayout.error = it
                }
            }
        })
        binding.emailPhone.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_NEXT -> {
                    binding.name.requestFocus()
                    if(binding.emailPhone.text?.isNullOrEmpty() == true){
                        binding.emailPhoneLayout.error = resources.getString(R.string.fill_field)
                    }
                    true
                }
                else -> false
            }
        }

        binding.name.doOnTextChanged { text, start, before, count ->
            validateName(text.toString())
        }
        loginActivityViewModel.nameErrorState.observe(this, {
            if(it.isEmpty()){
                binding.nameLayout.error = null
            }else{
                binding.nameLayout.error = it
            }
        })
        binding.name.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_NEXT -> {
                    binding.pass.requestFocus()
                    if(binding.name.text?.isNullOrEmpty() == true){
                        binding.nameLayout.error = resources.getString(R.string.fill_field)
                    }
                    true
                }
                else -> false
            }
        }


        binding.pass.doOnTextChanged { text, start, before, count ->
            validatePass(text.toString())
        }
        loginActivityViewModel.passErrorState.observe(this, {
            if(it.count { it == true } < 4){
                binding.passLayout.error = resources.getString(R.string.pass_matches_criteria)
            }else{
                binding.passLayout.error = null
            }
            if(it[0]){
                binding.imageView.setColorFilter(Color.parseColor("#4CAF50"))
            }else{
                binding.imageView.setColorFilter(Color.parseColor("#686868"))
            }

            if(it[1]){
                binding.imageView2.setColorFilter(Color.parseColor("#4CAF50"))
            }else{
                binding.imageView2.setColorFilter(Color.parseColor("#686868"))
            }

            if(it[2]){
                binding.imageView3.setColorFilter(Color.parseColor("#4CAF50"))
            }else{
                binding.imageView3.setColorFilter(Color.parseColor("#686868"))
            }

            if(it[3]){
                binding.imageView4.setColorFilter(Color.parseColor("#4CAF50"))
            }else{
                binding.imageView4.setColorFilter(Color.parseColor("#686868"))
            }
        })
        binding.pass.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_NEXT -> {
                    binding.pass2.requestFocus()
                    if(binding.pass.text?.isNullOrEmpty() == true){
                        binding.passLayout.error = resources.getString(R.string.fill_field)
                    }
                    true
                }
                else -> false
            }
        }



        binding.pass2.doOnTextChanged { text, start, before, count ->
            validatePass2(binding.pass.text.toString(), text.toString())
        }
        loginActivityViewModel.pass2ErrorState.observe(this, {
            if(it.isEmpty()){
                binding.pass2Layout.error = null
            }else{
                binding.pass2Layout.error = it
            }
        })

        loginActivityViewModel.stateData.observe(this, {
            val valid_att_all_li = it.values
            if(valid_att_all_li.count { it == true } == 4){
                binding.btnReg.isEnabled = true
            }else{
                binding.btnReg.isEnabled = false
            }
        })

        binding.imageView5.setOnClickListener {
            onBackPressed()
        }
    }

    private fun createFormatWatcher(){
        val slots = PhoneNumberUnderscoreSlotsParser().parseSlots("+7 (___) ___-__-__")
        val mask = MaskImpl.createTerminated(slots)
        formatWatcher = MaskFormatWatcher(mask)

    }

    private fun createMask(){
        formatWatcher?.installOn(phoneEditText)
    }

    private fun validateEmail(email: String){
        loginActivityViewModel.validateEmail(email)
    }

    private fun validatePhone(phone: String){
        loginActivityViewModel.validatePhone(phone)
    }

    private fun validateName(name: String){
        loginActivityViewModel.validateName(name)
    }

    private fun validatePass(pass: String){
        loginActivityViewModel.validatePass(pass)
    }

    private fun validatePass2(pass: String, pass2: String){
        loginActivityViewModel.validatePass2(pass, pass2)
    }
}