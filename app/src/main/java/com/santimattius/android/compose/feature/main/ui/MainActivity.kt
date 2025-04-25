package com.santimattius.android.compose.feature.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.santimattius.android.compose.core.ui.component.AppBar
import com.santimattius.android.compose.core.ui.component.DraggableGrid
import com.santimattius.android.compose.core.ui.component.DraggableItem
import com.santimattius.android.compose.core.ui.component.dragContainer
import com.santimattius.android.compose.core.ui.component.rememberGridDragDropState
import com.santimattius.android.compose.core.ui.theme.DragAndDropTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DragAndDropTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(topBar = { AppBar() }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            MainContent(
                state = state,
                onMove = viewModel::move
            )
        }
    }
}

@Composable
fun MainContent(state: MainUiState, onMove: (Int, Int) -> Unit) {
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.isFailure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error")
            }
        }

        else -> {
            val movies = state.data.toMutableStateList()
            MoviesContent(
                data = movies,
                onMove = onMove
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesContent(data: List<List<Content>>, onMove: (Int, Int) -> Unit) {
    val gridState = rememberLazyGridState()
    val dragDropState = rememberGridDragDropState(gridState, {_, _ -> })

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.dragContainer(dragDropState),
        state = gridState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        itemsIndexed(data[0], key = { _, item -> item }) { index, item ->
            DraggableItem(dragDropState, index) { isDragging ->
                CardGridItem(item = item, color = Color.Blue)
            }
        }

        item(span = {  GridItemSpan(3) }) {
            Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
        }

        itemsIndexed(data[1], key = { _, item -> item }) { index, item ->
            DraggableItem(dragDropState, index) { isDragging ->
                CardGridItem(item = item, color = Color.Red)
            }
        }
    }
}

private const val IMAGE_ASPECT_RATIO = 0.67f

@Composable
fun CardGridItem(
    modifier: Modifier = Modifier,
    color: Color,
    item: Content,
) {
    Card(
        modifier = modifier.height(100.dp).background(color = color),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp)
    ) {
        Text(text = item.title)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DragAndDropTheme {
        MainScreen()
    }
}