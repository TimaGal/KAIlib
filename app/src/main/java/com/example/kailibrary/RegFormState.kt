package com.example.kailibrary

import com.example.kailibrary.UseCases.*

data class RegFormState(val emailValidator: EmailValidator, val phoneValidator: PhoneValidator, val nameValidator: NameValidator, val passValidator: PassValidator, val pass2Validator: Pass2Validator) {

}