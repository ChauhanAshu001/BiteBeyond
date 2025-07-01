package com.nativenomad.bitebeyond.presentation.profile

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.presentation.navgraph.Routes
import com.nativenomad.bitebeyond.presentation.profile.components.ProfilePhoto
import com.nativenomad.bitebeyond.presentation.profile.components.ProfileTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel= hiltViewModel(),
                  navController: NavController) {
    val lightOrange = colorResource(id = R.color.lightOrange)

    val fullName = profileViewModel.name.collectAsState()
    val gender = profileViewModel.gender.collectAsState()
    val phone = profileViewModel.phoneNumber.collectAsState()
    val email = profileViewModel.email.collectAsState()

    val genderOptions = listOf("Male", "Female", "Other")
    var genderExpanded by remember { mutableStateOf(false) }

    val uiState=profileViewModel.uiState.collectAsState()
    val savingState = remember { mutableStateOf(false) }
    val savingErrorMessage = remember { mutableStateOf<String?>(null) }

    val context= LocalContext.current
    when(uiState.value){
        is ProfileEvent.Failed->{
            savingState.value=false
            savingErrorMessage.value="Failed"
        }
        is ProfileEvent.Loading->{
            savingState.value=true
            savingErrorMessage.value=null
        }
        else->{
            savingState.value=false
            savingErrorMessage.value=null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Personal Data",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Profile image with camera icon
        ProfilePhoto()

        Spacer(modifier = Modifier.height(24.dp))

        ProfileTextField("Full Name", fullName.value) { profileViewModel.setName(it) }

        Text("Gender", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Medium)
        ExposedDropdownMenuBox(
            expanded = genderExpanded,
            onExpandedChange = { genderExpanded = !genderExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = gender.value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = genderExpanded,
                onDismissRequest = { genderExpanded = false }
            ) {
                genderOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            profileViewModel.setGender(option)
                            genderExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        ProfileTextField("Phone", phone.value) { profileViewModel.setPhoneNumber(it) }
        ProfileTextField("Email", email.value) { profileViewModel.setEmail(it) }

        Spacer(modifier = Modifier.height(32.dp))

        // Save Button
        Text(text=savingErrorMessage.value?:"", color = Color.Red)

        Button(
            onClick = {
                if(profileViewModel.uid!="null"){
                    profileViewModel.uploadUserData()
                }
                else{

                    Toast.makeText(context,"Login Please",Toast.LENGTH_SHORT).show()
                    navController.navigate(Routes.SignUpScreen.route)
                }
                 },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = lightOrange),
            shape = RoundedCornerShape(25.dp)
        ) {

            Box(){
                AnimatedContent(targetState= savingState.value){ target->
                    if(target){
                        CircularProgressIndicator(
                            color=Color.White,
                            modifier=Modifier.size(24.dp)
                        )
                    }
                    else{
                        Text("Save", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

        }


        Spacer(modifier = Modifier.height(60.dp))
    }
}