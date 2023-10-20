package com.livefront.flickrsearch.viewmodels

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.livefront.flickrsearch.data.network.FlickrPhoto
import com.livefront.flickrsearch.data.network.FlickrPhotos
import com.livefront.flickrsearch.data.network.FlickrSearchResult
import com.livefront.flickrsearch.repository.FlickrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val flickrRepository: FlickrRepository
) : BaseViewModel<SearchViewModelState, SearchViewModelEvent, SearchViewModelAction>(
    initialState = savedStateHandle[SAVED_STATE_KEY] ?: SearchViewModelState()
){
    init {
        stateFlow
            .onEach { savedStateHandle[SAVED_STATE_KEY] = it }
            .launchIn(viewModelScope)

        onSearchTextUpdated(stateFlow.value.searchText)
    }

    override fun handleAction(action: SearchViewModelAction) {
        when (action) {
            is SearchViewModelAction.SearchTextUpdated -> onSearchTextUpdated(action.updatedSearchText)
            is SearchViewModelAction.SearchResultsReceived -> onSearchResultsReceived(action.searchResult)
            is SearchViewModelAction.SearchResultTouched -> onSearchResultTouched(action.result)
            is SearchViewModelAction.ErrorRetryTouched -> onErrorRetryTouched()
        }
    }

    private fun onSearchTextUpdated(searchText: String) {
        mutableStateFlow.update { it.copy(loading = true, searchText = searchText) }

        viewModelScope.launch {
            val result = flickrRepository.searchPhotos(searchText)
            sendAction(SearchViewModelAction.SearchResultsReceived(result))
        }
    }

    private fun onSearchResultsReceived(searchResult: FlickrSearchResult) {
        when(searchResult) {
            is FlickrSearchResult.Success -> {
                mutableStateFlow.update { it.copy(loading = false, photos = searchResult.value) }
            }
            is FlickrSearchResult.Error -> {
                mutableStateFlow.update { it.copy(loading = false, error = true) }
            }
        }
    }

    private fun onSearchResultTouched(result: FlickrPhoto) {
        sendEvent(SearchViewModelEvent.NavigateToDetails(result))
    }

    private fun onErrorRetryTouched() {
        mutableStateFlow.update { it.copy(error = false) }
        onSearchTextUpdated(stateFlow.value.searchText)
    }
}

@Parcelize
data class SearchViewModelState(
    val searchText: String = "",
    val photos: FlickrPhotos = FlickrPhotos(emptyList()),
    val loading: Boolean = true,
    val error: Boolean = false,
) : Parcelable

sealed class SearchViewModelEvent {
    data class NavigateToDetails(val result: FlickrPhoto) : SearchViewModelEvent()
}

sealed class SearchViewModelAction {
    data class SearchTextUpdated(val updatedSearchText: String) : SearchViewModelAction()
    data class SearchResultsReceived(val searchResult: FlickrSearchResult) : SearchViewModelAction()
    data class SearchResultTouched(val result: FlickrPhoto) : SearchViewModelAction()
    object ErrorRetryTouched: SearchViewModelAction()
}