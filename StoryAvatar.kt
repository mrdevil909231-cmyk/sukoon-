package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.InstaBlue
import com.example.ui.theme.StoryGradient

@Composable
fun StoryAvatar(
    avatarUrl: String,
    username: String,
    isViewed: Boolean = false,
    hasStory: Boolean = true,
    isOwnStory: Boolean = false,
    size: Dp = 68.dp,
    showUsername: Boolean = true,
    onClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 6.dp)
            .testTag("story_avatar_${username.replace(".", "_")}")
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(size + 8.dp)
        ) {
            // Story ring border
            val borderModifier = when {
                !hasStory -> Modifier
                isViewed -> Modifier.border(2.dp, Color.Gray.copy(alpha = 0.5f), CircleShape)
                else -> Modifier.border(
                    width = 2.5.dp,
                    brush = StoryGradient,
                    shape = CircleShape
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(size + 6.dp)
                    .then(borderModifier)
                    .padding(3.5.dp)
            ) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "$username's story",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape)
                )
            }

            // Blue plus badge for own story
            if (isOwnStory) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(InstaBlue)
                        .clickable { onAddClick() }
                        .testTag("add_story_badge")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Story",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        if (showUsername) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isOwnStory) "Your story" else username,
                fontSize = 11.5.sp,
                fontWeight = if (isViewed) FontWeight.Normal else FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.size(width = size + 10.dp, height = 16.dp)
            )
        }
    }
}
