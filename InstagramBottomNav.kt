package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ui.MainTab

@Composable
fun InstagramBottomNav(
    currentTab: MainTab,
    userAvatarUrl: String,
    onTabSelected: (MainTab) -> Unit,
    isDarkOverlay: Boolean = false
) {
    val navBackground = if (isDarkOverlay) Color.Black.copy(alpha = 0.9f) else MaterialTheme.colorScheme.background
    val iconTint = if (isDarkOverlay) Color.White else MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(navBackground)
            .navigationBarsPadding()
    ) {
        ColumnModifier(isDarkOverlay) {
            HorizontalDivider(
                color = if (isDarkOverlay) Color.White.copy(alpha = 0.15f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                thickness = 0.5.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable { onTabSelected(MainTab.HOME) }
                        .testTag("nav_tab_home"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (currentTab == MainTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                        contentDescription = "Home",
                        tint = iconTint,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // Explore
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable { onTabSelected(MainTab.EXPLORE) }
                        .testTag("nav_tab_explore"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (currentTab == MainTab.EXPLORE) Icons.Filled.Search else Icons.Outlined.Search,
                        contentDescription = "Search & Explore",
                        tint = iconTint,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // Create
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable { onTabSelected(MainTab.CREATE) }
                        .testTag("nav_tab_create"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (currentTab == MainTab.CREATE) Icons.Filled.AddBox else Icons.Outlined.AddBox,
                        contentDescription = "Create",
                        tint = iconTint,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // Reels
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable { onTabSelected(MainTab.REELS) }
                        .testTag("nav_tab_reels"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (currentTab == MainTab.REELS) Icons.Filled.VideoLibrary else Icons.Outlined.VideoLibrary,
                        contentDescription = "Reels",
                        tint = iconTint,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // Profile Avatar Tab
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable { onTabSelected(MainTab.PROFILE) }
                        .testTag("nav_tab_profile"),
                    contentAlignment = Alignment.Center
                ) {
                    val borderMod = if (currentTab == MainTab.PROFILE) {
                        Modifier.border(1.5.dp, iconTint, CircleShape).padding(2.dp)
                    } else Modifier

                    AsyncImage(
                        model = userAvatarUrl,
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(25.dp)
                            .then(borderMod)
                            .clip(CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
private fun ColumnModifier(isDark: Boolean, content: @Composable () -> Unit) {
    androidx.compose.foundation.layout.Column {
        content()
    }
}
