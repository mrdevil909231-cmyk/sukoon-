package com.example.data.model

data class User(
    val id: String,
    val username: String,
    val fullName: String,
    val avatarUrl: String,
    val isVerified: Boolean = false,
    val bio: String = "",
    val postsCount: Int = 0,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val isFollowing: Boolean = false,
    val storyNote: String = "",
    val isOnline: Boolean = false
)

data class Story(
    val id: String,
    val userId: String,
    val username: String,
    val userAvatar: String,
    val isVerified: Boolean = false,
    val imageUrl: String,
    val timestampText: String = "2h",
    val isViewed: Boolean = false,
    val caption: String = "",
    val isOwnStory: Boolean = false
)

data class Post(
    val id: String,
    val userId: String,
    val username: String,
    val userAvatar: String,
    val isVerified: Boolean = false,
    val imageUrl: String,
    val caption: String,
    val location: String = "",
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false,
    val timestampText: String = "1h ago",
    val commentsCount: Int = 0
)

data class Reel(
    val id: String,
    val userId: String,
    val username: String,
    val userAvatar: String,
    val isVerified: Boolean = false,
    val videoThumbnailUrl: String,
    val audioTrack: String,
    val caption: String,
    val likesCount: Int,
    val commentsCount: Int,
    val sharesCount: Int,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false,
    val isFollowing: Boolean = false
)

data class Comment(
    val id: String,
    val targetId: String, // postId or reelId
    val username: String,
    val userAvatar: String,
    val text: String,
    val timestampText: String = "Just now",
    val likesCount: Int = 0,
    val isLiked: Boolean = false
)

data class DirectMessage(
    val id: String,
    val threadId: String,
    val senderId: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isMe: Boolean = false,
    val reaction: String? = null
)

data class ChatThread(
    val id: String,
    val participant: User,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int = 0
)
