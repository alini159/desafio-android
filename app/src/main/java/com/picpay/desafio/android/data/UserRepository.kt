package com.picpay.desafio.android.data

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.domain.local.UserLocalDataSource
import com.picpay.desafio.android.domain.model.UserLocal
import com.picpay.desafio.android.domain.remote.UserRemoteDataSource

class UserRepositoryImp(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override fun getUsers(success: () -> Unit, failure: (String) -> Unit) {

        userRemoteDataSource.getUser(success = { users ->
            userLocalDataSource.insert(users)
            success()
        }, failure = failure)
    }

    override fun getUserDao(): LiveData<List<UserLocal>?> {
        return userLocalDataSource.getUser()
    }
}

interface UserRepository {
    fun getUsers(success: () -> Unit, failure: (String) -> Unit)
    fun getUserDao(): LiveData<List<UserLocal>?>
}