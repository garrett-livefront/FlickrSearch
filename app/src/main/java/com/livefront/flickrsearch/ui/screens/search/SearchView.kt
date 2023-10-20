package com.livefront.flickrsearch.ui.screens.search

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.livefront.flickrsearch.data.network.FlickrPhoto
import com.livefront.flickrsearch.data.network.FlickrPhotos
import com.livefront.flickrsearch.ui.EventsEffect
import com.livefront.flickrsearch.ui.ImageFromUrl
import com.livefront.flickrsearch.ui.Loading
import com.livefront.flickrsearch.viewmodels.SearchViewModel
import com.livefront.flickrsearch.viewmodels.SearchViewModelAction
import com.livefront.flickrsearch.viewmodels.SearchViewModelEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by searchViewModel.stateFlow.collectAsState()
    EventsEffect(searchViewModel) { event ->
        when (event) {
            is SearchViewModelEvent.NavigateToDetails -> {
                val resultJson = Gson().toJson(event.result)
                navController.navigate("detailScreen/${Uri.encode(resultJson)}")
            }
        }
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier
            .safeDrawingPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            SearchTopBar(
                scrollBehavior = scrollBehavior,
                searchTextValue = state.searchText,
                onSearchTextChanged = { searchValue ->
                    searchViewModel.trySendAction(SearchViewModelAction.SearchTextUpdated(searchValue))
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (state.error) {
                // TODO create an error composable
            } else if (state.loading) {
                Loading(modifier = Modifier.fillMaxSize()) // TODO fix the centering of this vertically
            } else {
                SearchResultContent(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
                    state.photos
                ) { photo ->
                    searchViewModel.trySendAction(SearchViewModelAction.SearchResultTouched(photo))
                }
            }
        }
    }
}

@Composable
private fun SearchResultContent(
    modifier: Modifier = Modifier,
    photos: FlickrPhotos,
    onPhotoClicked: (FlickrPhoto) -> Unit
) {
    when(LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(photos.items) { photo ->
                    PhotoResult(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPhotoClicked.invoke(photo) },
                        photo = photo
                    )

                }
            }
        }
        else -> {
            LazyColumn(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(photos.items) { photo ->
                    PhotoResult(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPhotoClicked.invoke(photo) },
                        photo = photo
                    )
                }
            }
        }
    }
}

@Composable
private fun PhotoResult(
    modifier: Modifier = Modifier,
    photo: FlickrPhoto
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ImageFromUrl(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f / 1f),
                imageUrl = photo.media.values.first()
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = photo.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}