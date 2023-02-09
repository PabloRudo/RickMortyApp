package com.pablogallardo.rickmortyapp.helpers.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectOnLifecycle(
    owner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: (T) -> Unit
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(state) {
            collect {
                block(it)
            }
        }
    }
}