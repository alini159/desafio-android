package com.picpay.desafio.android.domain.local

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.data.database.UserDao
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.model.UserLocal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserLocalDataSourceImp(private val dao: UserDao) : UserLocalDataSource {

    override fun insert(users: List<User>) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(converterToUserList(users))
        }
    }

    override fun getUser(): LiveData<List<UserLocal>?> {
        return dao.getUser()
    }

    private fun converterToUserList(users: List<User>): List<UserLocal> {
        return users.map { user ->
            UserLocal(
                img = user.img,
                name = user.name,
                id = user.id,
                username = user.username
            )
        }
    }
}

interface UserLocalDataSource {
    fun insert(users: List<User>)
    fun getUser(): LiveData<List<UserLocal>?>
}