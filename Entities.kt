package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val threadId: String,
    val senderId: String,
    val text: String,
    val timestamp: Long,
    val isMe: Boolean,
    val reaction: String?
)

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val username: String,
    val userAvatar: String,
    val isVerified: Boolean,
    val imageUrl: String,
    val caption: String,
    val location: String,
    val likesCount: Int,
    val isLiked: Boolean,
    val isSaved: Boolean,
    val timestampText: String,
    val commentsCount: Int
)

@Entity(tableName = "reels")
data class ReelEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val username: String,
    val userAvatar: String,
    val isVerified: Boolean,
    val videoThumbnailUrl: String,
    val audioTrack: String,
    val caption: String,
    val likesCount: Int,
    val commentsCount: Int,
    val sharesCount: Int,
    val isLiked: Boolean,
    val isSaved: Boolean,
    val isFollowing: Boolean
)

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: String,
    val targetId: String,
    val username: String,
    val userAvatar: String,
    val text: String,
    val timestampText: String,
    val likesCount: Int,
    val isLiked: Boolean
)

@Entity(tableName = "stories")
data class StoryEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val username: String,
    val userAvatar: String,
    val isVerified: Boolean,
    val imageUrl: String,
    val timestampText: String,
    val isViewed: Boolean,
    val caption: String,
    val isOwnStory: Boolean
)
