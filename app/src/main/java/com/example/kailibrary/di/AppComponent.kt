package com.example.kailibrary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kailibrary.LoginActivity
import com.example.kailibrary.RegFormState
import com.example.kailibrary.UseCases.*
import com.example.kailibrary.models.LoginActivityViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [AppModule::class])
interface AppComponent{
    fun inject(loginActivity: LoginActivity)

    @Component.Builder
    interface Builder{
        fun build(): AppComponent

        @BindsInstance
        fun loginActivity(loginActivity: LoginActivity): Builder
    }
}

@Module
class AppModule{
    @Provides
    fun provideLoginActivityViewModelInstance(loginActivity: LoginActivity): LoginActivityViewModel{
        return ViewModelProvider(loginActivity).get(LoginActivityViewModel::class.java)
    }
}

@Component(modules = [App1Module::class])
interface App1Component{

    fun inject(loginActivityViewModel: LoginActivityViewModel)

    @Component.Builder
    interface Builder{
        fun build(): App1Component
    }
}

@Module
class App1Module(){

    @Provides
    fun provideRegFormState(emailValidator: EmailValidator, phoneValidator: PhoneValidator, nameValidator: NameValidator, passValidator: PassValidator, pass2Validator: Pass2Validator): RegFormState{
        return RegFormState(emailValidator, phoneValidator, nameValidator, passValidator, pass2Validator)
    }

    @Provides
    fun provideEmailValidator(): EmailValidator = EmailValidator()

    @Provides
    fun providePhoneValidator(): PhoneValidator = PhoneValidator()

    @Provides
    fun provideNameValidator(): NameValidator = NameValidator()

    @Provides
    fun providePassValidator(): PassValidator = PassValidator()

    @Provides
    fun providePass2Validator(): Pass2Validator = Pass2Validator()

}



