package com.livefront.flickrsearch.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.livefront.flickrsearch.R
import com.livefront.flickrsearch.data.network.extractUsername
import com.livefront.flickrsearch.data.network.FlickrPhoto
import com.livefront.flickrsearch.ui.ImageFromUrl

/**
 * [Composable] for displaying the details of an image, including the top bar with the back button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    modifier: Modifier = Modifier,
    photo: FlickrPhoto,
    navController: NavController
) {
    Scaffold(
        modifier = modifier.safeDrawingPadding(),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "${photo.extractUsername()}'s Photo")
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable { navController.popBackStack() },
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Back Button",
                        tint = Color.Black
                    )
                }
            )
        }
    ) { paddingValues ->
        DetailContent(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            photo = photo
        )
    }
}

@Composable
private fun DetailContent(
    modifier: Modifier = Modifier,
    photo: FlickrPhoto
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ImageFromUrl(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp),
            imageUrl = photo.media.values.first(),
            showSize = true,
            contentDescription = stringResource(id = R.string.user_image)
        )
        Text(
            text = photo.title
        )
        TagsView(
            modifier = Modifier.fillMaxWidth(),
            tags = photo.tags
        )
    }
}
