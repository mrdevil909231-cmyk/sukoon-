package com.example.data

import com.example.data.local.CommentEntity
import com.example.data.local.MessageEntity
import com.example.data.local.PostEntity
import com.example.data.local.ReelEntity
import com.example.data.local.StoryEntity
import com.example.data.model.ChatThread
import com.example.data.model.User

object SampleData {

    val currentUser = User(
        id = "user_me",
        username = "aman_morya",
        fullName = "Aman Morya",
        avatarUrl = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400&auto=format&fit=crop&q=80",
        isVerified = false,
        bio = "📱 Android Developer & Tech Creator\n✨ Crafting smooth interfaces & experiences\n📍 New Delhi, India",
        postsCount = 18,
        followersCount = 2840,
        followingCount = 420,
        isFollowing = false,
        storyNote = "Coding this Insta clone 🚀🔥",
        isOnline = true
    )

    val sampleUsers = listOf(
        User(
            id = "user_virat",
            username = "virat.kohli",
            fullName = "Virat Kohli",
            avatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            bio = "Athlete | Father | Believer in hard work 🇮🇳",
            followersCount = 271000000,
            followingCount = 295,
            storyNote = "Match day focus 🏏🔥",
            isOnline = true
        ),
        User(
            id = "user_alia",
            username = "aliaabhatt",
            fullName = "Alia Bhatt",
            avatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            bio = "Actor • Producer • Sunshine seeker 🌻",
            followersCount = 85400000,
            followingCount = 512,
            storyNote = "Coffee & script ☕️✨",
            isOnline = true
        ),
        User(
            id = "user_nature",
            username = "nature_aesthetic",
            fullName = "Earth & Beyond",
            avatarUrl = "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            bio = "Daily doses of planet earth's finest moments 🌿",
            followersCount = 3400000,
            followingCount = 140,
            storyNote = "Aurora in Norway 🌌",
            isOnline = false
        ),
        User(
            id = "user_tech",
            username = "tech_insider",
            fullName = "Tech Trends",
            avatarUrl = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            bio = "Everything Next Gen • Gadgets & AI ⚡️",
            followersCount = 1890000,
            followingCount = 210,
            storyNote = "New Android tips ⚡️",
            isOnline = true
        ),
        User(
            id = "user_neha",
            username = "neha_sharma",
            fullName = "Neha Sharma",
            avatarUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=400&auto=format&fit=crop&q=80",
            isVerified = false,
            bio = "Design, travel & aesthetic food 🥐🎨",
            followersCount = 42000,
            followingCount = 380,
            storyNote = "Weekend vibes 🌸",
            isOnline = true
        ),
        User(
            id = "user_rohit",
            username = "rohit_wanderer",
            fullName = "Rohit Travels",
            avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=400&auto=format&fit=crop&q=80",
            isVerified = false,
            bio = "Wandering through 30+ countries 🎒⛰️",
            followersCount = 98000,
            followingCount = 610,
            storyNote = "Mountains calling 🏔️",
            isOnline = false
        )
    )

    fun getInitialStories(): List<StoryEntity> = listOf(
        StoryEntity(
            id = "story_me",
            userId = currentUser.id,
            username = "Your story",
            userAvatar = currentUser.avatarUrl,
            isVerified = false,
            imageUrl = "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=800&auto=format&fit=crop&q=80",
            timestampText = "30m",
            isViewed = false,
            caption = "Building smooth Android experiences 💻⚡️",
            isOwnStory = true
        ),
        StoryEntity(
            id = "story_virat",
            userId = "user_virat",
            username = "virat.kohli",
            userAvatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            imageUrl = "https://images.unsplash.com/photo-1517838277536-f5f99be501cd?w=800&auto=format&fit=crop&q=80",
            timestampText = "2h",
            isViewed = false,
            caption = "Morning training session done! Hard work always pays off 🏋️‍♂️💪",
            isOwnStory = false
        ),
        StoryEntity(
            id = "story_alia",
            userId = "user_alia",
            username = "aliaabhatt",
            userAvatar = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            imageUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=800&auto=format&fit=crop&q=80",
            timestampText = "4h",
            isViewed = false,
            caption = "Behind the scenes 🎬 Coffee is my best friend today! ☕️",
            isOwnStory = false
        ),
        StoryEntity(
            id = "story_nature",
            userId = "user_nature",
            username = "nature_aesthetic",
            userAvatar = "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            imageUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800&auto=format&fit=crop&q=80",
            timestampText = "6h",
            isViewed = false,
            caption = "Sunset over tropical waves 🌊 The colors tonight are surreal!",
            isOwnStory = false
        ),
        StoryEntity(
            id = "story_tech",
            userId = "user_tech",
            username = "tech_insider",
            userAvatar = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            imageUrl = "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=800&auto=format&fit=crop&q=80",
            timestampText = "8h",
            isViewed = false,
            caption = "Unboxing next generation flagship device! Thoughts? 📱📦",
            isOwnStory = false
        ),
        StoryEntity(
            id = "story_neha",
            userId = "user_neha",
            username = "neha_sharma",
            userAvatar = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=400&auto=format&fit=crop&q=80",
            isVerified = false,
            imageUrl = "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800&auto=format&fit=crop&q=80",
            timestampText = "11h",
            isViewed = false,
            caption = "Cosy rooftop evening with acoustic music 🎶✨",
            isOwnStory = false
        )
    )

    fun getInitialPosts(): List<PostEntity> = listOf(
        PostEntity(
            id = "post_1",
            userId = "user_virat",
            username = "virat.kohli",
            userAvatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            imageUrl = "https://images.unsplash.com/photo-1531415074868-036b107e775a?w=1000&auto=format&fit=crop&q=80",
            caption = "Grind never stops. Grateful for the continuous love and energy every single day! 💪🔥 #Cricket #Dedication #Grateful",
            location = "Melbourne Cricket Ground",
            likesCount = 1428590,
            isLiked = false,
            isSaved = false,
            timestampText = "2 HOURS AGO",
            commentsCount = 28420
        ),
        PostEntity(
            id = "post_2",
            userId = "user_alia",
            username = "aliaabhatt",
            userAvatar = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            imageUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=1000&auto=format&fit=crop&q=80",
            caption = "Sun-kissed mornings and peaceful thoughts. Take time to pause today ☀️✨ #MorningVibes #Peace #Glow",
            location = "London, United Kingdom",
            likesCount = 892300,
            isLiked = false,
            isSaved = false,
            timestampText = "5 HOURS AGO",
            commentsCount = 14190
        ),
        PostEntity(
            id = "post_3",
            userId = "user_nature",
            username = "nature_aesthetic",
            userAvatar = "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            imageUrl = "https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=1000&auto=format&fit=crop&q=80",
            caption = "Nature's greatest masterpiece 🏔️ Would you camp here under a million stars? ⛺️🌌 #Wanderlust #Alps #Explore",
            location = "Swiss Alps, Switzerland",
            likesCount = 230500,
            isLiked = false,
            isSaved = false,
            timestampText = "8 HOURS AGO",
            commentsCount = 3850
        ),
        PostEntity(
            id = "post_4",
            userId = "user_tech",
            username = "tech_insider",
            userAvatar = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            imageUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=1000&auto=format&fit=crop&q=80",
            caption = "The future of mobile computing & edge AI is arriving faster than expected. What is your most anticipated tech innovation? 📱🤖⚡️",
            location = "San Francisco, California",
            likesCount = 68400,
            isLiked = false,
            isSaved = false,
            timestampText = "12 HOURS AGO",
            commentsCount = 1290
        ),
        PostEntity(
            id = "post_5",
            userId = "user_neha",
            username = "neha_sharma",
            userAvatar = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=400&auto=format&fit=crop&q=80",
            isVerified = false,
            imageUrl = "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=1000&auto=format&fit=crop&q=80",
            caption = "Coffee date with an incredible book 📖 Cozy corners are my happy place ☕️🥐 #Aesthetic #CoffeeLover #ReadingCorner",
            location = "Hauz Khas Village, Delhi",
            likesCount = 19400,
            isLiked = false,
            isSaved = false,
            timestampText = "1 DAY AGO",
            commentsCount = 412
        )
    )

    fun getInitialReels(): List<ReelEntity> = listOf(
        ReelEntity(
            id = "reel_1",
            userId = "user_nature",
            username = "nature_aesthetic",
            userAvatar = "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            videoThumbnailUrl = "https://images.unsplash.com/photo-1518495973542-4542c06a5843?w=1080&auto=format&fit=crop&q=80",
            audioTrack = "♫ Waterfall Serenade • @chill_sounds",
            caption = "Hidden emerald waterfalls in Iceland 🇮🇸 You have to experience this sound in person! 🌊✨ #NatureReels #TravelVibes #Iceland",
            likesCount = 428190,
            commentsCount = 3840,
            sharesCount = 59100,
            isLiked = false,
            isSaved = false,
            isFollowing = false
        ),
        ReelEntity(
            id = "reel_2",
            userId = "user_tech",
            username = "tech_insider",
            userAvatar = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            videoThumbnailUrl = "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=1080&auto=format&fit=crop&q=80",
            audioTrack = "♫ Cyber Tech Beat • @synthwave_hub",
            caption = "3 Android productivity tricks 90% of people don't know! Double tap if this helped you 📲⚡️ #AndroidHacks #TechReels #PhoneTips",
            likesCount = 289400,
            commentsCount = 2150,
            sharesCount = 84200,
            isLiked = false,
            isSaved = false,
            isFollowing = true
        ),
        ReelEntity(
            id = "reel_3",
            userId = "user_virat",
            username = "virat.kohli",
            userAvatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            videoThumbnailUrl = "https://images.unsplash.com/photo-1517838277536-f5f99be501cd?w=1080&auto=format&fit=crop&q=80",
            audioTrack = "♫ Champion Energy • @beast_mode",
            caption = "Quick agility drill before match day 🏃‍♂️💨 Consistency builds champions! #TrainingDay #FitnessMotivation #NeverGiveUp",
            likesCount = 1940000,
            commentsCount = 24900,
            sharesCount = 198000,
            isLiked = false,
            isSaved = false,
            isFollowing = true
        ),
        ReelEntity(
            id = "reel_4",
            userId = "user_rohit",
            username = "rohit_wanderer",
            userAvatar = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=400&auto=format&fit=crop&q=80",
            isVerified = false,
            videoThumbnailUrl = "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=1080&auto=format&fit=crop&q=80",
            audioTrack = "♫ Starry Night Acoustic • @himalayan_tunes",
            caption = "Camping under milky way stars at 14,000 feet in Spiti Valley 🌌🏕️ Tag someone you would take here! #Spiti #StarGazing #Adventure",
            likesCount = 312000,
            commentsCount = 4210,
            sharesCount = 78500,
            isLiked = false,
            isSaved = false,
            isFollowing = false
        ),
        ReelEntity(
            id = "reel_5",
            userId = "user_alia",
            username = "aliaabhatt",
            userAvatar = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400&auto=format&fit=crop&q=80",
            isVerified = true,
            videoThumbnailUrl = "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?w=1080&auto=format&fit=crop&q=80",
            audioTrack = "♫ Golden Hour Glow • @aesthetic_sounds",
            caption = "Golden hour moments on set ✨ Smiling through the long shoot days! 🎬❤️ #GoldenHour #SetLife #PositiveEnergy",
            likesCount = 920000,
            commentsCount = 11200,
            sharesCount = 43000,
            isLiked = false,
            isSaved = false,
            isFollowing = false
        )
    )

    fun getInitialComments(): List<CommentEntity> = listOf(
        CommentEntity(
            id = "comm_1",
            targetId = "post_1",
            username = "rohit_wanderer",
            userAvatar = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=400&auto=format&fit=crop&q=80",
            text = "Pure inspiration King Kohli! Keep ruling the pitch 👑🔥",
            timestampText = "1h",
            likesCount = 890,
            isLiked = false
        ),
        CommentEntity(
            id = "comm_2",
            targetId = "post_1",
            username = "tech_insider",
            userAvatar = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=400&auto=format&fit=crop&q=80",
            text = "The dedication is unreal 🏏👏",
            timestampText = "45m",
            likesCount = 320,
            isLiked = false
        ),
        CommentEntity(
            id = "comm_3",
            targetId = "reel_1",
            username = "neha_sharma",
            userAvatar = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=400&auto=format&fit=crop&q=80",
            text = "Adding Iceland to my bucket list immediately! Breathtaking view 😍",
            timestampText = "2h",
            likesCount = 420,
            isLiked = false
        )
    )

    fun getInitialMessages(): List<MessageEntity> {
        val now = System.currentTimeMillis()
        return listOf(
            MessageEntity(
                id = "msg_1",
                threadId = "user_virat",
                senderId = "user_virat",
                text = "Hey Aman! Loved the smooth UI in your new Android app 🏏 Keep inspiring and building great stuff!",
                timestamp = now - 1800000,
                isMe = false,
                reaction = "❤️"
            ),
            MessageEntity(
                id = "msg_2",
                threadId = "user_virat",
                senderId = currentUser.id,
                text = "Thank you so much Virat sir! That means the world coming from you 🙏🔥",
                timestamp = now - 1200000,
                isMe = true,
                reaction = null
            ),
            MessageEntity(
                id = "msg_3",
                threadId = "user_virat",
                senderId = "user_virat",
                text = "Keep up the dedication bro! 💪",
                timestamp = now - 600000,
                isMe = false,
                reaction = "🔥"
            ),
            // Alia's thread
            MessageEntity(
                id = "msg_4",
                threadId = "user_alia",
                senderId = "user_alia",
                text = "Hey! That photography reel you shared was stunning ✨",
                timestamp = now - 3600000,
                isMe = false,
                reaction = "❤️"
            ),
            MessageEntity(
                id = "msg_5",
                threadId = "user_alia",
                senderId = currentUser.id,
                text = "Glad you liked it Alia! Planning a new travel series soon 📷",
                timestamp = now - 2400000,
                isMe = true,
                reaction = null
            ),
            // Tech Insider thread
            MessageEntity(
                id = "msg_6",
                threadId = "user_tech",
                senderId = "user_tech",
                text = "Hey Aman, sent you the beta invite for our Android Dev tool. Let me know your thoughts!",
                timestamp = now - 7200000,
                isMe = false,
                reaction = null
            ),
            // Neha thread
            MessageEntity(
                id = "msg_7",
                threadId = "user_neha",
                senderId = "user_neha",
                text = "Are we still meeting at the cafe this Sunday? ☕️ Let me know!",
                timestamp = now - 86400000,
                isMe = false,
                reaction = null
            ),
            // Rohit thread
            MessageEntity(
                id = "msg_8",
                threadId = "user_rohit",
                senderId = "user_rohit",
                text = "Check out this trek in Himachal! We definitely should plan this next month 🏔️",
                timestamp = now - 172800000,
                isMe = false,
                reaction = "🔥"
            )
        )
    }

    fun getChatThreads(users: List<User>, messages: List<MessageEntity>): List<ChatThread> {
        val userMap = users.associateBy { it.id }
        val threads = mutableListOf<ChatThread>()
        val grouped = messages.groupBy { it.threadId }

        for ((threadId, threadMsgs) in grouped) {
            val user = userMap[threadId] ?: continue
            val lastMsg = threadMsgs.maxByOrNull { it.timestamp }
            threads.add(
                ChatThread(
                    id = threadId,
                    participant = user,
                    lastMessage = lastMsg?.text ?: "Started a chat",
                    lastMessageTime = if (lastMsg != null) {
                        val diff = System.currentTimeMillis() - lastMsg.timestamp
                        when {
                            diff < 3600000 -> "${diff / 60000}m"
                            diff < 86400000 -> "${diff / 3600000}h"
                            else -> "${diff / 86400000}d"
                        }
                    } else "now",
                    unreadCount = if (lastMsg != null && !lastMsg.isMe) 1 else 0
                )
            )
        }

        // Add any remaining users without messages
        for (u in users) {
            if (threads.none { it.participant.id == u.id }) {
                threads.add(
                    ChatThread(
                        id = u.id,
                        participant = u,
                        lastMessage = "Active ${if (u.isOnline) "now" else "2h ago"}",
                        lastMessageTime = "1d",
                        unreadCount = 0
                    )
                )
            }
        }

        return threads.sortedByDescending { it.unreadCount }
    }
}
