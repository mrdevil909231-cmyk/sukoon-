package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.InstagramRepository
import com.example.data.SampleData
import com.example.data.local.AppDatabase
import com.example.data.local.CommentEntity
import com.example.data.local.MessageEntity
import com.example.data.local.PostEntity
import com.example.data.local.ReelEntity
import com.example.data.local.StoryEntity
import com.example.data.model.ChatThread
import com.example.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainTab {
    HOME,
    EXPLORE,
    CREATE,
    REELS,
    PROFILE
}

class InstagramViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = InstagramRepository(AppDatabase.getDatabase(application))

    val currentTab = MutableStateFlow(MainTab.HOME)

    val posts: StateFlow<List<PostEntity>> = repository.posts.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val reels: StateFlow<List<ReelEntity>> = repository.reels.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val stories: StateFlow<List<StoryEntity>> = repository.stories.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val allMessages: StateFlow<List<MessageEntity>> = repository.allMessages.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val chatThreads: StateFlow<List<ChatThread>> = combine(allMessages) { messages ->
        SampleData.getChatThreads(SampleData.sampleUsers, messages.first())
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    // Active full screen navigation state
    private val _viewingStory = MutableStateFlow<StoryEntity?>(null)
    val viewingStory: StateFlow<StoryEntity?> = _viewingStory.asStateFlow()

    private val _isDirectMessagesOpen = MutableStateFlow(false)
    val isDirectMessagesOpen: StateFlow<Boolean> = _isDirectMessagesOpen.asStateFlow()

    private val _activeChatUser = MutableStateFlow<User?>(null)
    val activeChatUser: StateFlow<User?> = _activeChatUser.asStateFlow()

    private val _activeCommentsTargetId = MutableStateFlow<String?>(null)
    val activeCommentsTargetId: StateFlow<String?> = _activeCommentsTargetId.asStateFlow()

    val activeComments: StateFlow<List<CommentEntity>> = _activeCommentsTargetId.flatMapLatest { targetId ->
        if (targetId != null) {
            repository.getCommentsForTarget(targetId)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val currentChatMessages: StateFlow<List<MessageEntity>> = _activeChatUser.flatMapLatest { user ->
        if (user != null) {
            repository.getMessagesForThread(user.id)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    // User profile state
    private val _currentUserState = MutableStateFlow(SampleData.currentUser)
    val currentUserState: StateFlow<User> = _currentUserState.asStateFlow()
    val currentUser: User get() = _currentUserState.value

    fun syncAuthenticatedUser(firebaseUser: com.google.firebase.auth.FirebaseUser) {
        val emailPrefix = firebaseUser.email?.substringBefore("@") ?: "user"
        val cleanUsername = firebaseUser.displayName?.lowercase()?.replace(" ", "_") ?: emailPrefix
        _currentUserState.value = User(
            id = firebaseUser.uid,
            username = cleanUsername,
            fullName = firebaseUser.displayName ?: "Sukoon Creator",
            avatarUrl = firebaseUser.photoUrl?.toString() ?: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400&auto=format&fit=crop&q=80",
            isVerified = false,
            bio = "✨ Creator on Sukoon\n📱 Sharing moments & vibes\n📍 India",
            postsCount = 0,
            followersCount = 120,
            followingCount = 45,
            isFollowing = false,
            storyNote = "Active on Sukoon ✨",
            isOnline = true
        )
    }

    fun selectTab(tab: MainTab) {
        currentTab.value = tab
    }

    fun toggleLikePost(post: PostEntity) {
        viewModelScope.launch {
            repository.toggleLikePost(post)
        }
    }

    fun doubleTapLikePost(post: PostEntity) {
        if (!post.isLiked) {
            viewModelScope.launch {
                repository.toggleLikePost(post)
            }
        }
    }

    fun toggleSavePost(post: PostEntity) {
        viewModelScope.launch {
            repository.toggleSavePost(post)
        }
    }

    fun toggleLikeReel(reel: ReelEntity) {
        viewModelScope.launch {
            repository.toggleLikeReel(reel)
        }
    }

    fun toggleSaveReel(reel: ReelEntity) {
        viewModelScope.launch {
            repository.toggleSaveReel(reel)
        }
    }

    fun toggleFollowReel(reel: ReelEntity) {
        viewModelScope.launch {
            repository.toggleFollowReel(reel)
        }
    }

    fun openStory(story: StoryEntity) {
        _viewingStory.value = story
        viewModelScope.launch {
            repository.markStoryViewed(story.id)
        }
    }

    fun closeStory() {
        _viewingStory.value = null
    }

    fun nextStory() {
        val current = _viewingStory.value ?: return
        val list = stories.value
        val index = list.indexOfFirst { it.id == current.id }
        if (index in 0 until list.size - 1) {
            openStory(list[index + 1])
        } else {
            closeStory()
        }
    }

    fun prevStory() {
        val current = _viewingStory.value ?: return
        val list = stories.value
        val index = list.indexOfFirst { it.id == current.id }
        if (index > 0) {
            openStory(list[index - 1])
        }
    }

    fun replyToStory(story: StoryEntity, replyText: String) {
        if (replyText.isBlank()) return
        viewModelScope.launch {
            val formatted = "Replied to story: \"$replyText\""
            repository.sendMessage(story.userId, formatted)
        }
    }

    fun openDirectMessages() {
        _isDirectMessagesOpen.value = true
    }

    fun closeDirectMessages() {
        _isDirectMessagesOpen.value = false
    }

    fun openChat(user: User) {
        _activeChatUser.value = user
    }

    fun closeChat() {
        _activeChatUser.value = null
    }

    fun sendMessage(text: String) {
        val user = _activeChatUser.value ?: return
        viewModelScope.launch {
            repository.sendMessage(user.id, text)
        }
    }

    fun addReactionToMessage(messageId: String, reaction: String?) {
        viewModelScope.launch {
            repository.updateMessageReaction(messageId, reaction)
        }
    }

    fun openComments(targetId: String) {
        _activeCommentsTargetId.value = targetId
    }

    fun closeComments() {
        _activeCommentsTargetId.value = null
    }

    fun addComment(text: String) {
        val targetId = _activeCommentsTargetId.value ?: return
        viewModelScope.launch {
            repository.addComment(targetId, text)
        }
    }

    fun createPost(imageUrl: String, caption: String, location: String) {
        viewModelScope.launch {
            repository.addPost(imageUrl, caption, location)
            currentTab.value = MainTab.HOME
        }
    }

    fun createReel(videoUrl: String, caption: String, audioTrack: String) {
        viewModelScope.launch {
            repository.addReel(videoUrl, caption, audioTrack)
            currentTab.value = MainTab.REELS
        }
    }

    fun createStory(imageUrl: String, caption: String) {
        viewModelScope.launch {
            repository.addStory(imageUrl, caption)
            currentTab.value = MainTab.HOME
        }
    }
}
