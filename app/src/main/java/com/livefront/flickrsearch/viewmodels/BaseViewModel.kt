package com.livefront.flickrsearch.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel classes used with composables
 * use hiltViewModel to inject a view model into a composable.
 *
 * @param S is the state type passed in
 * @param E is the base type for events used with the [eventFlow]
 * @param A is the base type for actions used with the [actionChannel]
 */
abstract class BaseViewModel<S, E, A>(
    initialState: S
) : ViewModel() {
    protected val mutableStateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    val stateFlow: StateFlow<S> = mutableStateFlow.asStateFlow()

    protected val eventChannel: Channel<E> = Channel(capacity = Channel.UNLIMITED)
    val eventFlow: Flow<E> = eventChannel.receiveAsFlow()

    private val _actionChannel: Channel<A> = Channel(capacity = Channel.UNLIMITED)
    val actionChannel: SendChannel<A> = _actionChannel


    init {
        viewModelScope.launch {
            _actionChannel
                .consumeAsFlow()
                .collect { handleAction(it) }
        }
    }

    protected abstract fun handleAction(action: A)

    fun trySendAction(action: A) { actionChannel.trySend(action) }

    protected suspend fun sendAction(action: A) { actionChannel.send(action) }

    protected fun sendEvent(event: E) { viewModelScope.launch { eventChannel.send(event) } }

    companion object {
        const val SAVED_STATE_KEY = "state"
    }
}