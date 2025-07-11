package com.nativenomad.adminbitebeyond.presentation.profile.components.myAccount

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfileRestaurantImage(
    imageUrl: String,
    onImageSelected: (Uri) -> Unit
) {
    val currentSelectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            currentSelectedImageUri.value = uri
            onImageSelected(uri)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        // Show the selected image or fallback states
        when {
            currentSelectedImageUri.value != null -> {
                AsyncImage(
                    model = currentSelectedImageUri.value.toString(),
                    contentDescription = "Selected Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            imageUrl.isNotEmpty() -> {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Placeholder Image",
                        tint = Color.DarkGray,
                        modifier = Modifier.size(48.dp)
                    )
                    Text("Tap to choose image")
                }
            }
        }

        // Overlay pencil icon at bottom-end
        Box(
            modifier = Modifier
                .clickable { launcher.launch(arrayOf("image/*")) }
                .align(Alignment.BottomEnd)
                .padding(12.dp)
                .size(36.dp)
                .background(Color.White.copy(alpha = 0.8f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Icon",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
