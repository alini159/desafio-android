package com.picpay.desafio.android.domain.interfaces

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.model.UserLocal

interface UserLocalDataSource {
    fun insert(users: List<User>)
    fun getUser(): LiveData<List<UserLocal>?>
}