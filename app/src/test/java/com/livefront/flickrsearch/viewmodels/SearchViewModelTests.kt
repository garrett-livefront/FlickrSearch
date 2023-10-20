package com.livefront.flickrsearch.viewmodels

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.livefront.flickrsearch.MainDispatcherExtension
import com.livefront.flickrsearch.data.network.FlickrPhoto
import com.livefront.flickrsearch.data.network.FlickrPhotos
import com.livefront.flickrsearch.data.network.FlickrSearchResult
import com.livefront.flickrsearch.repository.FlickrRepository
import com.livefront.flickrsearch.repository.FlickrService
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.api.Assertions.assertEquals

class SearchViewModelTests {

    @MockK
    val flickrRepository: FlickrRepository = FlickrRepository(mockkClass(FlickrService::class))

    @Test
    fun `verify SearchTextUpdated action updated search text properly`() = runBlocking {
        val viewModel = SearchViewModel(SavedStateHandle(), flickrRepository)
        viewModel.stateFlow.test {
            viewModel.actionChannel.trySend(SearchViewModelAction.SearchTextUpdated("Testing"))

            assertEquals(SearchViewModelState(loading = true), awaitItem()) // init
            assertEquals(SearchViewModelState(loading = true, searchText = "Testing"), awaitItem())
        }
    }

    @Test
    fun `verify SearchResultsReceived updates state with good data`() = runBlocking {
        val viewModel = SearchViewModel(SavedStateHandle(), flickrRepository)
        viewModel.stateFlow.test {
            viewModel.actionChannel.trySend(
                SearchViewModelAction.SearchResultsReceived(FlickrSearchResult.Success(value = SAMPLE_FLICKR_PHOTOS))
            )

            assertEquals(SearchViewModelState(loading = true), awaitItem()) // init
            assert(awaitItem().photos.items.isNotEmpty())
        }
    }

    @Test
    fun `verify SearchResultsReceived updates state with error`() = runBlocking {
        val viewModel = SearchViewModel(SavedStateHandle(), flickrRepository)
        viewModel.stateFlow.test {
            viewModel.actionChannel.trySend(
                SearchViewModelAction.SearchResultsReceived(FlickrSearchResult.Error("Error"))
            )

            assertEquals(SearchViewModelState(loading = true), awaitItem()) // init
            val item = awaitItem()
            assert(item.photos.items.isEmpty())
            assert(!item.loading)
            assert(item.error)
        }
    }

    @Test
    fun `verify SearchResultTouched action triggers NavigateToDetails event`() = runTest {
        val viewModel = SearchViewModel(SavedStateHandle(), flickrRepository)
        viewModel.eventFlow.test {
            viewModel.actionChannel.trySend(SearchViewModelAction.SearchResultTouched(SAMPLE_FLICKR_PHOTO))
            assertEquals(SearchViewModelEvent.NavigateToDetails(SAMPLE_FLICKR_PHOTO), awaitItem())
        }
    }

    @Test
    fun `verify initial state with no savedStateHandle data`() {
        val viewModel = SearchViewModel(SavedStateHandle(), flickrRepository)
        assertEquals(DEFAULT_STATE, viewModel.stateFlow.value)
    }

    @Test
    fun `verify initial state set to savedStateHandle data`() {
        val handle = SavedStateHandle(mapOf(BaseViewModel.SAVED_STATE_KEY to SAVED_STATE))
        val viewModel = SearchViewModel(handle, flickrRepository)
        assertEquals(SAVED_STATE, viewModel.stateFlow.value)
    }

    @Test
    fun `verify ErrorRetryTouched resets error state to false`() = runBlocking {
        val handle = SavedStateHandle(mapOf(BaseViewModel.SAVED_STATE_KEY to ERROR_STATE))
        val viewModel = SearchViewModel(handle, flickrRepository)
        viewModel.stateFlow.test {
            assert(awaitItem().error) // init
            viewModel.actionChannel.trySend(SearchViewModelAction.ErrorRetryTouched)
            assert(!awaitItem().error) // reset
        }
    }

    companion object {
        @JvmStatic
        @RegisterExtension
        val mainDispatcherExtension = MainDispatcherExtension()

        private val DEFAULT_STATE = SearchViewModelState()

        private val SAVED_STATE = SearchViewModelState(
            searchText = "Testing",
            loading = true
        )

        private val ERROR_STATE = SearchViewModelState(
            error = true
        )

        private val SAMPLE_FLICKR_PHOTO = FlickrPhoto(
            title = "Test",
            media = emptyMap(),
            author = "Garrett",
            tags = "cars, trucks, cool, things",
            dateTaken =  ""
        )

        private val SAMPLE_FLICKR_PHOTOS = FlickrPhotos(
            items = listOf(SAMPLE_FLICKR_PHOTO)
        )
    }
}