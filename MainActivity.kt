package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.auth.AuthManager
import com.example.data.SampleData
import com.example.ui.InstagramViewModel
import com.example.ui.MainTab
import com.example.ui.components.CommentsBottomSheet
import com.example.ui.components.InstagramBottomNav
import com.example.ui.screens.ChatThreadScreen
import com.example.ui.screens.CreateContentScreen
import com.example.ui.screens.DirectMessagesScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ReelsScreen
import com.example.ui.screens.SignInScreen
import com.example.ui.screens.StoryViewerScreen
import com.example.ui.theme.InstagramTheme

class MainActivity : ComponentActivity() {
    private val viewModel: InstagramViewModel by viewModels()
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authManager = AuthManager(this)
        authManager.attemptAutoSignIn(lifecycleScope)
        enableEdgeToEdge()

        setContent {
            InstagramTheme(darkTheme = true) {
                val authUser by authManager.currentUser.collectAsStateWithLifecycle()
                val scope = rememberCoroutineScope()

                LaunchedEffect(authUser) {
                    val user = authUser
                    if (user != null) {
                        viewModel.syncAuthenticatedUser(user)
                    }
                }

                if (authUser == null) {
                    SignInScreen(
                        authManager = authManager,
                        onSignInSuccess = { user ->
                            viewModel.syncAuthenticatedUser(user)
                        }
                    )
                } else {
                    InstagramApp(
                        viewModel = viewModel,
                        onSignOut = {
                            authManager.signOut(scope)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun InstagramApp(
    viewModel: InstagramViewModel,
    onSignOut: () -> Unit = {}
) {
    val currentUser by viewModel.currentUserState.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val posts by viewModel.posts.collectAsStateWithLifecycle()
    val reels by viewModel.reels.collectAsStateWithLifecycle()
    val stories by viewModel.stories.collectAsStateWithLifecycle()
    val chatThreads by viewModel.chatThreads.collectAsStateWithLifecycle()
    val currentChatMessages by viewModel.currentChatMessages.collectAsStateWithLifecycle()

    val viewingStory by viewModel.viewingStory.collectAsStateWithLifecycle()
    val isDmOpen by viewModel.isDirectMessagesOpen.collectAsStateWithLifecycle()
    val activeChatUser by viewModel.activeChatUser.collectAsStateWithLifecycle()
    val activeCommentsTargetId by viewModel.activeCommentsTargetId.collectAsStateWithLifecycle()
    val activeComments by viewModel.activeComments.collectAsStateWithLifecycle()

    val unreadDmCount = chatThreads.sumOf { it.unreadCount }

    // Back button handling in reverse priority order
    BackHandler(enabled = viewingStory != null) {
        viewModel.closeStory()
    }

    BackHandler(enabled = activeChatUser != null) {
        viewModel.closeChat()
    }

    BackHandler(enabled = activeChatUser == null && isDmOpen) {
        viewModel.closeDirectMessages()
    }

    BackHandler(enabled = activeCommentsTargetId != null) {
        viewModel.closeComments()
    }

    BackHandler(enabled = viewingStory == null && !isDmOpen && activeChatUser == null && currentTab != MainTab.HOME) {
        viewModel.selectTab(MainTab.HOME)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Main Tab Content inside Scaffold
        Scaffold(
            bottomBar = {
                // Hide bottom nav when in fullscreen story, chat, or create
                if (viewingStory == null && !isDmOpen && activeChatUser == null && currentTab != MainTab.CREATE) {
                    InstagramBottomNav(
                        currentTab = currentTab,
                        userAvatarUrl = currentUser.avatarUrl,
                        onTabSelected = { viewModel.selectTab(it) },
                        isDarkOverlay = currentTab == MainTab.REELS
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = if (currentTab == MainTab.REELS) innerPadding.calculateBottomPadding() / 2 else innerPadding.calculateBottomPadding())
            ) {
                when (currentTab) {
                    MainTab.HOME -> {
                        HomeScreen(
                            currentUser = currentUser,
                            stories = stories,
                            posts = posts,
                            unreadDmCount = unreadDmCount,
                            onStoryClick = { viewModel.openStory(it) },
                            onAddStoryClick = { viewModel.selectTab(MainTab.CREATE) },
                            onPostLike = { viewModel.toggleLikePost(it) },
                            onDoubleTapLike = { viewModel.doubleTapLikePost(it) },
                            onPostComment = { viewModel.openComments(it.id) },
                            onPostShare = { viewModel.openDirectMessages() },
                            onPostSave = { viewModel.toggleSavePost(it) },
                            onDmClick = { viewModel.openDirectMessages() }
                        )
                    }

                    MainTab.EXPLORE -> {
                        ExploreScreen(
                            posts = posts,
                            reels = reels,
                            onItemClick = { itemId ->
                                val matchingReel = reels.find { it.id == itemId }
                                if (matchingReel != null) {
                                    viewModel.selectTab(MainTab.REELS)
                                } else {
                                    viewModel.openComments(itemId)
                                }
                            }
                        )
                    }

                    MainTab.CREATE -> {
                        CreateContentScreen(
                            onClose = { viewModel.selectTab(MainTab.HOME) },
                            onPostCreated = { url, caption, location ->
                                viewModel.createPost(url, caption, location)
                            },
                            onReelCreated = { url, caption, audio ->
                                viewModel.createReel(url, caption, audio)
                            },
                            onStoryCreated = { url, caption ->
                                viewModel.createStory(url, caption)
                            }
                        )
                    }

                    MainTab.REELS -> {
                        ReelsScreen(
                            reels = reels,
                            onLikeReel = { viewModel.toggleLikeReel(it) },
                            onDoubleTapLike = { viewModel.toggleLikeReel(it) },
                            onCommentClick = { viewModel.openComments(it.id) },
                            onShareClick = { viewModel.openDirectMessages() },
                            onSaveClick = { viewModel.toggleSaveReel(it) },
                            onFollowClick = { viewModel.toggleFollowReel(it) },
                            onCameraClick = { viewModel.selectTab(MainTab.CREATE) }
                        )
                    }

                    MainTab.PROFILE -> {
                        ProfileScreen(
                            user = currentUser,
                            userPosts = posts.filter { it.userId == currentUser.id || it.userId == "user_me" },
                            onPostClick = { viewModel.openComments(it.id) },
                            onSignOut = onSignOut
                        )
                    }
                }
            }
        }

        // Direct Messages (Inbox) Full Screen Overlay
        if (isDmOpen && activeChatUser == null) {
            DirectMessagesScreen(
                currentUser = currentUser,
                threads = chatThreads,
                onBackClick = { viewModel.closeDirectMessages() },
                onThreadClick = { user -> viewModel.openChat(user) }
            )
        }

        // 1-on-1 Chat Thread Screen Overlay
        if (activeChatUser != null) {
            ChatThreadScreen(
                user = activeChatUser!!,
                messages = currentChatMessages,
                onBackClick = { viewModel.closeChat() },
                onSendMessage = { text -> viewModel.sendMessage(text) },
                onAddReaction = { msgId, reaction ->
                    viewModel.addReactionToMessage(msgId, reaction)
                }
            )
        }

        // Full Screen Story Viewer Overlay
        val currentStory = viewingStory
        if (currentStory != null) {
            StoryViewerScreen(
                story = currentStory,
                onClose = { viewModel.closeStory() },
                onNext = { viewModel.nextStory() },
                onPrev = { viewModel.prevStory() },
                onReply = { text ->
                    viewModel.replyToStory(currentStory, text)
                    viewModel.closeStory()
                    val targetUser = SampleData.sampleUsers.find { it.id == currentStory.userId }
                    if (targetUser != null) {
                        viewModel.openChat(targetUser)
                    }
                }
            )
        }

        // Comments Bottom Sheet
        if (activeCommentsTargetId != null) {
            CommentsBottomSheet(
                comments = activeComments,
                onDismiss = { viewModel.closeComments() },
                onPostComment = { text -> viewModel.addComment(text) }
            )
        }
    }
}
