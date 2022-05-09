package com.picpay.desafio.android.domain.model

sealed class UserResponse {
    object Success : UserResponse()
    data class Failure(val error: String) : UserResponse()
    object Loading : UserResponse()
}
