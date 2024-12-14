package com.umtech.flowtest.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.umtech.flowtest.data.model.User

@Composable
fun UserDetailsScreen(navController: NavController, user: User) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Image banner as a header
        user.url?.let {
            item {
                Image(
                    painter = rememberImagePainter(it),
                    contentDescription = "Banner Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Posts in cells
        item {
            Text(text = "Posts:", modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Post items with padding and spacing between cells
        items(user.posts ?: emptyList()) { post ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp), // Left, right, and bottom padding
                elevation = CardDefaults.cardElevation(8.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Title: ${post.title}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Body: ${post.body}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}