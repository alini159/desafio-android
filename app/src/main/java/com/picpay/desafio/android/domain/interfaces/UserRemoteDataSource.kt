package com.picpay.desafio.android.domain.interfaces

import com.picpay.desafio.android.domain.model.User

interface UserRemoteDataSource {
    fun getUser(success: (List<User>) -> Unit, failure: (String) -> Unit)
}