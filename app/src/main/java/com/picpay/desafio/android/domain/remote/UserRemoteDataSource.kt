package com.picpay.desafio.android.domain.remote

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.data.apiclient.PicPayService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteDataSourceImp(private val service: PicPayService) : UserRemoteDataSource {

    override fun getUser(success: (List<User>) -> Unit, failure: (String) -> Unit) {
        val call = service.getUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        success(user)
                    }
                }else{
                    failure(response.message())
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.message?.let {
                    failure(it)
                }
            }

        })
    }
}
interface UserRemoteDataSource {

    fun getUser(success: (List<User>)-> Unit, failure: (String) -> Unit)
}