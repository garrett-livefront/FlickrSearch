package com.livefront.flickrsearch.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.livefront.flickrsearch.R

@Composable
fun ImageFromUrl(
    modifier: Modifier = Modifier,
    imageUrl: String,
    showSize: Boolean = false
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )

    when(painter.state) {
        is AsyncImagePainter.State.Success -> {
            Column {
                Image(
                    modifier = modifier,
                    painter = painter,
                    contentScale = ContentScale.Crop,
                    contentDescription = null // TODO fix this
                )
                if (showSize) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        text = "${painter.intrinsicSize.width.toInt()}x${painter.intrinsicSize.height.toInt()}"
                    )
                }
            }
        }
        else -> {
            Column(
                modifier = modifier.background(Color.LightGray),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.ic_image),
                    contentDescription = null, // TODO
                    tint = Color.Gray
                )
            }
        }
    }
}