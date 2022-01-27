package com.example.themoviedb.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.themoviedb.data.model.Results
import com.example.themoviedb.data.repository.MainRepository
import com.example.themoviedb.utils.Resource
import kotlinx.coroutines.Dispatchers
import android.util.Patterns

import android.text.TextUtils


class MainViewModel(private val mainRepository: MainRepository, application: Application) :
    AndroidViewModel(application) {
    var emailMessage = MutableLiveData<String>()
    var passwordMessage = MutableLiveData<String>()
    var addFragment = MutableLiveData<Boolean>(false)
    fun getMovieList() = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getMoviesList()))
        } catch (e: Exception) {

            emit(Resource.error(data = null, message = e.message ?: "Exception Occured!!"))
        }

    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun validateValue(target: String, passwordText: String?) {
        if (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
            emailMessage.value= ""
            if (!TextUtils.isEmpty(passwordText)) {
                if (passwordText!!.length >= 8) {
                    passwordMessage.value = ""
                    addFragment.value=true
                } else {
                    passwordMessage.value = "Password should contain 8 to 15 Characters"
                }
            } else {
                passwordMessage.value = "Please enter Password"
            }
        }else{
            emailMessage.value= "Please enter valid Email"
        }

    }

}