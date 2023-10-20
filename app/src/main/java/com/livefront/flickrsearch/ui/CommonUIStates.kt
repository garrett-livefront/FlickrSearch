package com.livefront.flickrsearch.ui

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.livefront.flickrsearch.R
import com.livefront.flickrsearch.ui.theme.FlickrSearchTheme

/**
 * [Composable] that can be used anywhere to display a loading progress indicator that's been
 * centered and share all the same sizes and colors.
 */
@Composable
fun Loading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp)
        )
    }
}

/**
 * [Composable] that can be used to display an error message across the app in a consistent way.
 */
@Composable
fun ErrorState(
    modifier: Modifier = Modifier,
    @StringRes errorMessage: Int,
    onTryAgainPressed: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, CenterVertically)
    ) {
        Text(
            text = "Error!"
        )
        Text(
            text = stringResource(id = errorMessage),
            textAlign = TextAlign.Center
        )
        Button(onClick = onTryAgainPressed) {
            Text(text = "Try Again.")
        }
    }
}

@Composable
@Preview(name = "dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "light", showBackground = true)
private fun LoadingPreview() {
    FlickrSearchTheme {
        Loading(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
@Preview(name = "dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "light", showBackground = true)
private fun ErrorPreview() {
    FlickrSearchTheme {
        ErrorState(
            modifier = Modifier.fillMaxSize(),
            errorMessage = R.string.search_error_message,
            onTryAgainPressed = { }
        )
    }
}
