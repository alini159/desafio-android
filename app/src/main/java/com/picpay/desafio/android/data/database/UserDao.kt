package com.picpay.desafio.android.data.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.picpay.desafio.android.domain.model.UserLocal

@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun insert(user: List<UserLocal>)

    @Query("SELECT * from userlocal ")
    fun getUser() : LiveData<List<UserLocal>?>
}