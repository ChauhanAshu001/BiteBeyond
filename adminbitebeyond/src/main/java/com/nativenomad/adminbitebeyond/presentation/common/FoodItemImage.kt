package com.nativenomad.adminbitebeyond.presentation.common

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nativenomad.adminbitebeyond.R

@Composable
fun FoodItemImage(onImageSelected:(Uri)->Unit) {


//    val selectedImageUri= menuAddViewModel.imageUri.collectAsState()
    val currentSelectedImageUri = remember { mutableStateOf<Uri?>(null) }
    // Image picker launcher
    val context= LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument() /* If persistent uri wasn't needed then this line would be
        contract = ActivityResultContracts.GetContent()*/
    ) { uri: Uri? ->
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)        /*In viewmodel I will process this uri on Dispatchers.IO thread pool (in saveUserData function) instead of Dispatchers.Main thread pool hence uri need to persist thread change so this is why this line is added*/
            currentSelectedImageUri.value=uri
            onImageSelected(uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Show selected image or placeholder
        if (currentSelectedImageUri.value.toString().isNotEmpty()) {
            AsyncImage(
                model = currentSelectedImageUri.value.toString(),
                contentDescription = "food Photo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Default Photo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .padding(16.dp),
                tint = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { launcher.launch(arrayOf("image/*"))  }, // Launch image picker
            colors = ButtonDefaults.buttonColors(
                contentColor = colorResource(id= R.color.white),
                containerColor = colorResource(id = R.color.lightGreen)
            )
        ) {
            Text(text = "Choose food's Photo")
        }
    }
}