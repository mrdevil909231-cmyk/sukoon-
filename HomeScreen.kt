package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.local.PostEntity
import com.example.ui.theme.InstaBlue
import com.example.ui.theme.InstaPink
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PostCard(
    post: PostEntity,
    onLikeClick: () -> Unit,
    onDoubleTapLike: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit,
    onUserClick: () -> Unit = {},
    onOptionsClick: () -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
            .testTag("post_card_${post.id}")
    ) {
        // Post Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = post.userAvatar,
                contentDescription = "${post.username} avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable { onUserClick() }
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onUserClick() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = post.username,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.5.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    if (post.isVerified) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verified",
                            tint = InstaBlue,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
                if (post.location.isNotBlank()) {
                    Text(
                        text = post.location,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(
                onClick = onOptionsClick,
                modifier = Modifier.testTag("post_options_button_${post.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Post options",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Post Image with Double Tap Heart Detector
        DoubleTapLikeArea(
            onDoubleTap = onDoubleTapLike,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.DarkGray)
        ) {
            AsyncImage(
                model = post.imageUrl,
                contentDescription = "Post photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f)
            )
        }

        // Action Buttons Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Like Button with bouncy scale animation
            val heartScale by animateFloatAsState(
                targetValue = if (post.isLiked) 1.15f else 1.0f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = "heartScale"
            )
            val heartColor by animateColorAsState(
                targetValue = if (post.isLiked) InstaPink else MaterialTheme.colorScheme.onBackground,
                label = "heartColor"
            )

            IconButton(
                onClick = onLikeClick,
                modifier = Modifier
                    .scale(heartScale)
                    .testTag("post_like_button_${post.id}")
            ) {
                Icon(
                    imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Like",
                    tint = heartColor,
                    modifier = Modifier.size(26.dp)
                )
            }

            // Comment Button
            IconButton(
                onClick = onCommentClick,
                modifier = Modifier.testTag("post_comment_button_${post.id}")
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline,
                    contentDescription = "Comment",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Share / Send Button
            IconButton(
                onClick = onShareClick,
                modifier = Modifier.testTag("post_share_button_${post.id}")
            ) {
                Icon(
                    imageVector = Icons.Outlined.Send,
                    contentDescription = "Share",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(23.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bookmark / Save Button
            IconButton(
                onClick = onSaveClick,
                modifier = Modifier.testTag("post_save_button_${post.id}")
            ) {
                Icon(
                    imageVector = if (post.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Save",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        // Likes Count
        val formattedLikes = NumberFormat.getNumberInstance(Locale.US).format(post.likesCount)
        Text(
            text = "$formattedLikes likes",
            fontWeight = FontWeight.Bold,
            fontSize = 13.5.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 14.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Caption
        val annotatedCaption = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)) {
                append("${post.username} ")
            }
            append(post.caption)
        }

        Text(
            text = annotatedCaption,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = if (isExpanded) Int.MAX_VALUE else 2,
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .clickable { isExpanded = !isExpanded }
        )

        // View comments button
        if (post.commentsCount > 0) {
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "View all ${NumberFormat.getNumberInstance(Locale.US).format(post.commentsCount)} comments",
                fontSize = 12.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .clickable { onCommentClick() }
            )
        }

        // Timestamp
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = post.timestampText,
            fontSize = 10.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
            modifier = Modifier.padding(horizontal = 14.dp)
        )
    }
}
