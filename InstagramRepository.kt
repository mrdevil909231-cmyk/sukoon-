package com.example.data

import com.example.data.local.AppDatabase
import com.example.data.local.CommentEntity
import com.example.data.local.MessageEntity
import com.example.data.local.PostEntity
import com.example.data.local.ReelEntity
import com.example.data.local.StoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID

class InstagramRepository(private val database: AppDatabase) {

    private val postDao = database.postDao()
    private val reelDao = database.reelDao()
    private val storyDao = database.storyDao()
    private val messageDao = database.messageDao()
    private val commentDao = database.commentDao()

    val posts: Flow<List<PostEntity>> = postDao.getAllPosts()
    val reels: Flow<List<ReelEntity>> = reelDao.getAllReels()
    val stories: Flow<List<StoryEntity>> = storyDao.getAllStories()
    val allMessages: Flow<List<MessageEntity>> = messageDao.getAllMessages()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            seedDatabaseIfEmpty()
        }
    }

    private suspend fun seedDatabaseIfEmpty() {
        val existingPosts = postDao.getAllPosts().first()
        if (existingPosts.isEmpty()) {
            postDao.insertPosts(SampleData.getInitialPosts())
        }
        val existingReels = reelDao.getAllReels().first()
        if (existingReels.isEmpty()) {
            reelDao.insertReels(SampleData.getInitialReels())
        }
        val existingStories = storyDao.getAllStories().first()
        if (existingStories.isEmpty()) {
            storyDao.insertStories(SampleData.getInitialStories())
        }
        val existingMessages = messageDao.getAllMessages().first()
        if (existingMessages.isEmpty()) {
            for (msg in SampleData.getInitialMessages()) {
                messageDao.insertMessage(msg)
            }
        }
        val existingComments = commentDao.getCommentsForTarget("post_1").first()
        if (existingComments.isEmpty()) {
            commentDao.insertComments(SampleData.getInitialComments())
        }
    }

    fun getMessagesForThread(threadId: String): Flow<List<MessageEntity>> {
        return messageDao.getMessagesForThread(threadId)
    }

    fun getCommentsForTarget(targetId: String): Flow<List<CommentEntity>> {
        return commentDao.getCommentsForTarget(targetId)
    }

    suspend fun toggleLikePost(post: PostEntity) {
        val newLiked = !post.isLiked
        val newCount = if (newLiked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
        postDao.updateLike(post.id, newLiked, newCount)
    }

    suspend fun toggleSavePost(post: PostEntity) {
        postDao.updateSave(post.id, !post.isSaved)
    }

    suspend fun toggleLikeReel(reel: ReelEntity) {
        val newLiked = !reel.isLiked
        val newCount = if (newLiked) reel.likesCount + 1 else (reel.likesCount - 1).coerceAtLeast(0)
        reelDao.updateLike(reel.id, newLiked, newCount)
    }

    suspend fun toggleSaveReel(reel: ReelEntity) {
        reelDao.updateSave(reel.id, !reel.isSaved)
    }

    suspend fun toggleFollowReel(reel: ReelEntity) {
        reelDao.updateFollow(reel.id, !reel.isFollowing)
    }

    suspend fun addComment(targetId: String, text: String) {
        if (text.isBlank()) return
        val comment = CommentEntity(
            id = UUID.randomUUID().toString(),
            targetId = targetId,
            username = SampleData.currentUser.username,
            userAvatar = SampleData.currentUser.avatarUrl,
            text = text.trim(),
            timestampText = "Just now",
            likesCount = 0,
            isLiked = false
        )
        commentDao.insertComment(comment)
    }

    suspend fun sendMessage(threadId: String, text: String) {
        if (text.isBlank()) return
        val myMessage = MessageEntity(
            id = UUID.randomUUID().toString(),
            threadId = threadId,
            senderId = SampleData.currentUser.id,
            text = text.trim(),
            timestamp = System.currentTimeMillis(),
            isMe = true,
            reaction = null
        )
        messageDao.insertMessage(myMessage)

        // Realistic automated reply after brief moment
        val partner = SampleData.sampleUsers.find { it.id == threadId }
        if (partner != null) {
            val replyText = when {
                text.contains("hello", ignoreCase = true) || text.contains("hi", ignoreCase = true) ->
                    "Hey Aman! Great to hear from you 🙌 How is everything going?"
                text.contains("reel", ignoreCase = true) || text.contains("video", ignoreCase = true) ->
                    "That was awesome! Really loved the transitions and aesthetics 🔥"
                text.contains("post", ignoreCase = true) || text.contains("photo", ignoreCase = true) ->
                    "Stunning shot! The colors and composition look super crisp 📸"
                text.contains("story", ignoreCase = true) ->
                    "Haha nice story! Loved the vibe ✨"
                else ->
                    "Thanks for reaching out! Absolutely love this new Instagram app experience 🚀"
            }
            val replyMessage = MessageEntity(
                id = UUID.randomUUID().toString(),
                threadId = threadId,
                senderId = partner.id,
                text = replyText,
                timestamp = System.currentTimeMillis() + 1500,
                isMe = false,
                reaction = "❤️"
            )
            messageDao.insertMessage(replyMessage)
        }
    }

    suspend fun updateMessageReaction(messageId: String, reaction: String?) {
        messageDao.updateReaction(messageId, reaction)
    }

    suspend fun markStoryViewed(storyId: String) {
        storyDao.markViewed(storyId)
    }

    suspend fun addStory(imageUrl: String, caption: String) {
        val newStory = StoryEntity(
            id = UUID.randomUUID().toString(),
            userId = SampleData.currentUser.id,
            username = "Your story",
            userAvatar = SampleData.currentUser.avatarUrl,
            isVerified = false,
            imageUrl = imageUrl.ifBlank { "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=800&auto=format&fit=crop&q=80" },
            timestampText = "Just now",
            isViewed = false,
            caption = caption,
            isOwnStory = true
        )
        storyDao.insertStory(newStory)
    }

    suspend fun addPost(imageUrl: String, caption: String, location: String) {
        val newPost = PostEntity(
            id = UUID.randomUUID().toString(),
            userId = SampleData.currentUser.id,
            username = SampleData.currentUser.username,
            userAvatar = SampleData.currentUser.avatarUrl,
            isVerified = false,
            imageUrl = imageUrl.ifBlank { "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=1000&auto=format&fit=crop&q=80" },
            caption = caption,
            location = location,
            likesCount = 1,
            isLiked = true,
            isSaved = false,
            timestampText = "JUST NOW",
            commentsCount = 0
        )
        postDao.insertPost(newPost)
    }

    suspend fun addReel(videoThumbnailUrl: String, caption: String, audioTrack: String) {
        val newReel = ReelEntity(
            id = UUID.randomUUID().toString(),
            userId = SampleData.currentUser.id,
            username = SampleData.currentUser.username,
            userAvatar = SampleData.currentUser.avatarUrl,
            isVerified = false,
            videoThumbnailUrl = videoThumbnailUrl.ifBlank { "https://images.unsplash.com/photo-1518495973542-4542c06a5843?w=1080&auto=format&fit=crop&q=80" },
            audioTrack = audioTrack.ifBlank { "♫ Original Audio • @${SampleData.currentUser.username}" },
            caption = caption,
            likesCount = 1,
            commentsCount = 0,
            sharesCount = 0,
            isLiked = true,
            isSaved = false,
            isFollowing = false
        )
        reelDao.insertReel(newReel)
    }
}
