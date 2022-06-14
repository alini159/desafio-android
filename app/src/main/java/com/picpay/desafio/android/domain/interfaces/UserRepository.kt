package com.picpay.desafio.android.domain.interfaces

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.domain.model.UserLocal

interface UserRepository {
    fun getUsers(success: () -> Unit, failure: (String) -> Unit): LiveData<List<UserLocal>?>
}