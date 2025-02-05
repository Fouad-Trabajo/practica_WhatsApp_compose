package com.example.practicawhatsappcompose


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    TopAppBar()
                    ListChats()
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(y = (-150).dp, x = (-20).dp)
                        .zIndex(1f)
                ) {
                    FabButton()
                }
                Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                    BottomNavigation()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopAppBar()
            ListChats()
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = (-150).dp, x = (-20).dp)
                .zIndex(1f)
        ) {
            FabButton()
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavigation()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    val context = LocalContext.current
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text(text = "WhatsApp") },
        actions = {
            IconButton(onClick = {
                Toast.makeText(context, "Buscar chat", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun BottomNavigation() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Chats", "Novedades", "Comunidad", "Llamadas")
    val selectedIcons = listOf(
        Icons.Filled.Email,
        Icons.Filled.Notifications,
        Icons.Filled.Person,
        Icons.Filled.Phone
    )
    val unselectedIcons = listOf(
        Icons.Filled.MailOutline,
        Icons.Outlined.Notifications,
        Icons.Filled.Person,
        Icons.Outlined.Phone
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item
                    )
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

@Composable
fun FabButton() {
    val context = LocalContext.current
    FloatingActionButton(
        onClick = { Toast.makeText(context, "Añadir nuevo chat", Toast.LENGTH_SHORT).show() }
    ) {
        Icon(Icons.Filled.Add, stringResource(id = R.string.fab_button))
    }
}

data class Item(val id: String, val title: String, val imageChat: Int, val description: String)

@Composable
fun ListChats() {
    LazyColumn(
        flingBehavior = ScrollableDefaults.flingBehavior(),
        state = rememberLazyListState(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = "Chats",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            val myList = (1..15).map {
                Item(
                    id = it.toString(),
                    title = "Chat $it",
                    imageChat = R.drawable.img_top,
                    description = "Descripción del chat $it",
                )
            }
            items(myList) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                        ),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_top),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp),
                        contentDescription = stringResource(R.string.image_chat_description)
                    )
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = it.title, style = MaterialTheme.typography.titleMedium)
                        Text(text = it.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    )
}
