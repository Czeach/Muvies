package com.czech.muvies.utils

import android.util.Log
import retrofit2.Response
import java.io.IOException

open class BaseRepository {
    suspend fun <T: Any> safeCall(call: suspend()-> Response<T>, error: String): T? {
        val result = moviesApiOutput(call, error)
        var output: T? = null
        when(result) {
            is Output.Success ->
                output = result.output
            is Output.Error ->
                Log.e("Error", "The $error and the ${result.exception}")
        }
        return output
    }

    private suspend fun <T: Any> moviesApiOutput(call: suspend()-> Response<T>, error: String): Output<T> {
        val response = call.invoke()
        return if (response.isSuccessful)
            Output.Success(response.body()!!)
        else
            Output.Error(IOException("OOps.. Something went wrong due to $error"))
    }
}