package com.livefront.flickrsearch.ui.screens.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.livefront.flickrsearch.ui.theme.FlickrSearchTheme

/**
 * Fun little [Composable] view for displaying the tags on an image
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsView(
    modifier: Modifier = Modifier,
    tags: String
) {
    val listOfTags: List<String>
    if (tags.trim().isNotEmpty()) {
        listOfTags = tags.split(" ")
    } else {
        return
    }

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        maxItemsInEachRow = 300
    ) {
        listOfTags.forEach { Tag(tag = it) }
    }
}

@Composable
private fun Tag(
    modifier: Modifier = Modifier,
    tag: String
) {
    Box(
        modifier = modifier.background(Color.LightGray, RoundedCornerShape(4.dp))
    ) {
        Text(
            modifier = modifier.padding(vertical = 4.dp, horizontal = 16.dp),
            text = tag,
            maxLines = 1
        )
    }
}

@Composable
@Preview(name = "dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "light", showBackground = true)
private fun SearchTopBarEmptyPreview() {
    FlickrSearchTheme {
        TagsView(tags = "some cool tags these are for a rad preview")
    }
}
