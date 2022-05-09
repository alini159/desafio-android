package com.picpay.desafio.android.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.domain.model.UserLocal
import com.picpay.desafio.android.data.UserRepository
import com.picpay.desafio.android.domain.model.UserResponse

class UserListViewModel(private val repository: UserRepository) : ViewModel() {

    private val _userResponseLiveData = MutableLiveData<UserResponse>(UserResponse.Loading)
    val liveData: LiveData<UserResponse> = _userResponseLiveData

    init {
        repository.getUsers(success = {
            _userResponseLiveData.postValue(UserResponse.Success)
        }, failure = {
            //adicionar uma string Com mensagem de erro melhor
            _userResponseLiveData.postValue(UserResponse.Failure("Tente mais tarde"))
        })
    }

    fun getUser(): LiveData<List<UserLocal>?> {
        return repository.getUserDao()
    }
}