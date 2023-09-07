package com.tridiv.tridivroytigeritproject.data.domain.common

sealed class ResultData<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : ResultData<T>()
    data class Error(val exception: Exception) : ResultData<Nothing>()
    object NoBodySuccess : ResultData<Nothing>()
    companion object {
        fun buildError(message: String?): Error {
            return Error(Exception(message))
        }

    }
}
