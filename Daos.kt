package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE threadId = :threadId ORDER BY timestamp ASC")
    fun getMessagesForThread(threadId: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Query("UPDATE messages SET reaction = :reaction WHERE id = :id")
    suspend fun updateReaction(id: String, reaction: String?)
}

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Query("UPDATE posts SET isLiked = :isLiked, likesCount = :likesCount WHERE id = :id")
    suspend fun updateLike(id: String, isLiked: Boolean, likesCount: Int)

    @Query("UPDATE posts SET isSaved = :isSaved WHERE id = :id")
    suspend fun updateSave(id: String, isSaved: Boolean)

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun deletePost(id: String)
}

@Dao
interface ReelDao {
    @Query("SELECT * FROM reels")
    fun getAllReels(): Flow<List<ReelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReels(reels: List<ReelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReel(reel: ReelEntity)

    @Query("UPDATE reels SET isLiked = :isLiked, likesCount = :likesCount WHERE id = :id")
    suspend fun updateLike(id: String, isLiked: Boolean, likesCount: Int)

    @Query("UPDATE reels SET isSaved = :isSaved WHERE id = :id")
    suspend fun updateSave(id: String, isSaved: Boolean)

    @Query("UPDATE reels SET isFollowing = :isFollowing WHERE id = :id")
    suspend fun updateFollow(id: String, isFollowing: Boolean)

    @Query("DELETE FROM reels WHERE id = :id")
    suspend fun deleteReel(id: String)
}

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments WHERE targetId = :targetId ORDER BY id ASC")
    fun getCommentsForTarget(targetId: String): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentEntity>)
}

@Dao
interface StoryDao {
    @Query("SELECT * FROM stories")
    fun getAllStories(): Flow<List<StoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(stories: List<StoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: StoryEntity)

    @Query("UPDATE stories SET isViewed = 1 WHERE id = :id")
    suspend fun markViewed(id: String)

    @Query("DELETE FROM stories WHERE id = :id")
    suspend fun deleteStory(id: String)
}
