package com.nativenomad.adminbitebeyond.presentation.enteringScreens.locationInfo

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nativenomad.adminbitebeyond.R
import com.nativenomad.adminbitebeyond.ui.theme.BiteBeyondTheme


@Composable
fun LocationInfoScreen(onEvent:(LocationInfoNavigationEvent)->Unit,
                       viewmodel:LocationInfoViewModel= hiltViewModel(),
) {

    val pincode=viewmodel.pincode.collectAsState()
    val state=viewmodel.state.collectAsState()
    val country=viewmodel.country.collectAsState()
    val address=viewmodel.address.collectAsState()
    val uiState = viewmodel.uiState.collectAsState()
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val loading= remember{ mutableStateOf(false) }

    when(uiState.value){
        is LocationInfoEvents.Error->{
            loading.value=false
            errorMessage.value="Failed"
        }
        is LocationInfoEvents.Loading->{
            loading.value=true
            errorMessage.value=null
        }
        else->{
            loading.value=false
            errorMessage.value=null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top=100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Enter Location Information of your Restaurant",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = state.value,
            onValueChange = {viewmodel.setState(it)},
            label = { Text("State") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = country.value,
            onValueChange = {viewmodel.setCountry(it)},
            label = { Text("Country") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pincode.value,
            onValueChange = {viewmodel.setPincode(it)},
            label = { Text("Pincode") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = address.value,
            onValueChange = { viewmodel.setAddress(it) },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(text=errorMessage.value?:"", color = Color.Red)

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onEvent(LocationInfoNavigationEvent.NavigateToMenuAdd)
                    viewmodel.saveRestaurantData()
                },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AnimatedContent(targetState = loading.value) {target->
                    if(target){
                        CircularProgressIndicator(
                            color= Color.White,
                            modifier=Modifier.size(24.dp)
                        )
                    }
                    else{
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next",
                            tint = colorResource(id=R.color.lightGreen),
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun LocationInfoScreenPreview() {
    BiteBeyondTheme {
        LocationInfoScreen(onEvent={})
    }
}