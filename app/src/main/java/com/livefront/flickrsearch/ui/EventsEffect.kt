package com.livefront.flickrsearch.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.livefront.flickrsearch.viewmodels.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * [Composable] function used to watch for events from the view model on the eventFlow
 */
@Composable
fun <E> EventsEffect(
    viewModel: BaseViewModel<*, E, *>,
    handler: (E) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow
            .onEach { handler.invoke(it) }
            .launchIn(this)
    }
}