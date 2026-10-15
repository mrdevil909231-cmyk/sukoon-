package com.example.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.local.StoryEntity
import com.example.data.model.User

@Composable
fun StoryTray(
    currentUser: User,
    stories: List<StoryEntity>,
    onStoryClick: (StoryEntity) -> Unit,
    onAddStoryClick: () -> Unit
) {
    val ownStory = stories.find { it.isOwnStory }
    val friendStories = stories.filter { !it.isOwnStory }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("story_tray"),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
    ) {
        // Current user item
        item {
            StoryAvatar(
                avatarUrl = currentUser.avatarUrl,
                username = "Your story",
                isViewed = ownStory?.isViewed ?: false,
                hasStory = ownStory != null,
                isOwnStory = true,
                onClick = {
                    if (ownStory != null) onStoryClick(ownStory) else onAddStoryClick()
                },
                onAddClick = onAddStoryClick
            )
        }

        items(friendStories, key = { it.id }) { story ->
            StoryAvatar(
                avatarUrl = story.userAvatar,
                username = story.username,
                isViewed = story.isViewed,
                hasStory = true,
                isOwnStory = false,
                onClick = { onStoryClick(story) }
            )
        }
    }

    HorizontalDivider(
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
        thickness = 0.5.dp,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}
