package com.picpay.desafio.android.data

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.domain.interfaces.UserLocalDataSource
import com.picpay.desafio.android.domain.interfaces.UserRemoteDataSource
import com.picpay.desafio.android.domain.interfaces.UserRepository
import com.picpay.desafio.android.domain.model.UserLocal

class UserRepositoryImp(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override fun getUsers(
        success: () -> Unit, failure: (String) -> Unit
    ): LiveData<List<UserLocal>?> {
        userRemoteDataSource.getUser(
            success = { users ->
                userLocalDataSource.insert(users)
                success()
            }, failure =
            failure
        )
        return userLocalDataSource.getUser()
    }
}

