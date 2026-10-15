package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.InstaBlue

@Composable
fun CreateContentScreen(
    onClose: () -> Unit,
    onPostCreated: (imageUrl: String, caption: String, location: String) -> Unit,
    onReelCreated: (videoUrl: String, caption: String, audioTrack: String) -> Unit,
    onStoryCreated: (imageUrl: String, caption: String) -> Unit
) {
    // 0 = Post, 1 = Reel, 2 = Story
    var selectedType by remember { mutableIntStateOf(0) }

    val presetImages = listOf(
        Pair("Tropical Sunset 🌅", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=1000&auto=format&fit=crop&q=80"),
        Pair("Modern Workspace 💻", "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=1000&auto=format&fit=crop&q=80"),
        Pair("Mountain Peaks 🏔️", "https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=1000&auto=format&fit=crop&q=80"),
        Pair("Fitness Grind 🏋️‍♂️", "https://images.unsplash.com/photo-1517838277536-f5f99be501cd?w=1000&auto=format&fit=crop&q=80"),
        Pair("Cozy Cafe ☕️", "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=1000&auto=format&fit=crop&q=80"),
        Pair("Future Tech 🤖", "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=1000&auto=format&fit=crop&q=80")
    )

    var selectedMediaUrl by remember { mutableStateOf(presetImages[0].second) }
    var captionText by remember { mutableStateOf("") }
    var locationText by remember { mutableStateOf("New Delhi, India") }
    var audioText by remember { mutableStateOf("♫ Trending Original Audio") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .testTag("create_content_screen")
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancel",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = when (selectedType) {
                    0 -> "New Post"
                    1 -> "New Reel"
                    else -> "New Story"
                },
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    when (selectedType) {
                        0 -> onPostCreated(selectedMediaUrl, captionText, locationText)
                        1 -> onReelCreated(selectedMediaUrl, captionText, audioText)
                        2 -> onStoryCreated(selectedMediaUrl, captionText)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = InstaBlue),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                modifier = Modifier.height(34.dp).testTag("share_content_button")
            ) {
                Text(
                    text = "Share",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }

        // Mode Selector: Post, Reel, Story
        TabRow(
            selectedTabIndex = selectedType,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            divider = { HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)) }
        ) {
            Tab(
                selected = selectedType == 0,
                onClick = { selectedType = 0 },
                text = { Text("POST", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
            )
            Tab(
                selected = selectedType == 1,
                onClick = { selectedType = 1 },
                text = { Text("REEL", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
            )
            Tab(
                selected = selectedType == 2,
                onClick = { selectedType = 2 },
                text = { Text("STORY", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Selected Media Preview
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .aspectRatio(if (selectedType == 0) 1.2f else 0.75f)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = selectedMediaUrl,
                contentDescription = "Selected media",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Preset media picker
        Text(
            text = "Choose Photo / Video Preset:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(presetImages) { (title, url) ->
                val isSelected = selectedMediaUrl == url
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { selectedMediaUrl = url }
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(68.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                width = if (isSelected) 2.5.dp else 0.5.dp,
                                color = if (isSelected) InstaBlue else Color.Gray.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title.split(" ")[0],
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Caption Field
        OutlinedTextField(
            value = captionText,
            onValueChange = { captionText = it },
            placeholder = { Text("Write a caption or thoughts...", fontSize = 13.5.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("create_caption_input"),
            shape = RoundedCornerShape(12.dp),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Specific fields depending on mode
        if (selectedType == 0) {
            // Location for Post
            OutlinedTextField(
                value = locationText,
                onValueChange = { locationText = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = InstaBlue
                    )
                },
                label = { Text("Add location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        } else if (selectedType == 1) {
            // Audio track for Reel
            OutlinedTextField(
                value = audioText,
                onValueChange = { audioText = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = "Audio track",
                        tint = InstaBlue
                    )
                },
                label = { Text("Audio Track") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
