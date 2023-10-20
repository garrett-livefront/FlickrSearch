package com.livefront.flickrsearch.ui.screens.search

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.livefront.flickrsearch.R
import com.livefront.flickrsearch.ui.theme.FlickrSearchTheme


/**
 * [Composable] for the top bar on the search screen. includes the collapsable title and
 * the search bar itself
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    searchTextValue: String,
    onSearchTextChanged:(String) -> Unit
)
{
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.search_top_bar_title)
                )
            },
            scrollBehavior = scrollBehavior
        )

        SearchTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            searchTextValue = searchTextValue,
            hintId = R.string.search_text_field_hint,
            onSearchTextChanged = onSearchTextChanged
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier,
    searchTextValue: String,
    @StringRes hintId: Int,
    onSearchTextChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isFocused = remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf(searchTextValue) }

    TextField(
        modifier = modifier.onFocusChanged { isFocused.value = it.isFocused },
        value = searchText,
        onValueChange = { textValue ->
            searchText = textValue
            onSearchTextChanged.invoke(searchText)
        },
        label = { HintText(text = stringResource(id = hintId)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        leadingIcon = if (isFocused.value) {
            {
                Icon(
                    modifier = Modifier.clickable { focusManager.clearFocus() },
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null, // TODO
                    tint = Color.Black
                )
            }
        } else {
            null
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    searchText = ""
                    onSearchTextChanged.invoke(searchText)
                },
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null, // TODO
                tint = Color.Black
            )
        }
    )
}

@Composable
private fun HintText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Gray
) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.05.sp,
        color = color
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(name = "dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "light", showBackground = true)
private fun SearchTopBarPreview() {
    FlickrSearchTheme {
        SearchTopBar(
            scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
            searchTextValue = "porcupine",
            onSearchTextChanged = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(name = "dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "light", showBackground = true)
private fun SearchTopBarEmptyPreview() {
    FlickrSearchTheme {
        SearchTopBar(
            scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
            searchTextValue = "",
            onSearchTextChanged = { }
        )
    }
}
