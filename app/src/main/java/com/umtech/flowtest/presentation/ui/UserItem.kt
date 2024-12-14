package com.umtech.flowtest.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.umtech.flowtest.data.model.User

@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display thumbnail using Coil's AsyncImage
            AsyncImage(
                model = user.thumbnailUrl,
                contentDescription = "Thumbnail for ${user.name}",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Display user details in a column
            Column {
                Text(text = user.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Posts: ${user.postCount}", style = MaterialTheme.typography.bodySmall)
            }

            // Spacer for aligning the arrow at the end
            Spacer(modifier = Modifier.weight(1f))

            // Forward arrow icon
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Forward",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    UserItem(
        user = User(
            userId = 1,
            name = "Umer",
            postCount = 5,
            thumbnailUrl = "https://dummyimage.com/150/d32776&text=User+4",
            url = "url"
        ),
        onClick = { /* Do nothing for preview */ }

    )

}