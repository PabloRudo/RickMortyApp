package com.pablogallardo.rickmortyapp.helpers.extensions

import com.pablogallardo.rickmortyapp.data.ResultError

inline fun <R> resultOf(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (error: ResultError) {
        Result.failure(error)
    }
}