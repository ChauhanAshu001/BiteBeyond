package com.nativenomad.adminbitebeyond.presentation.profile.components.myAccount

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.nativenomad.adminbitebeyond.R


@Composable
fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colorResource(id = R.color.lightGreen),
            unfocusedTextColor = colorResource(id = R.color.lightGreen),
            focusedBorderColor = colorResource(id = R.color.lightGreen),
            unfocusedBorderColor = colorResource(id = R.color.lightGreen),
            cursorColor = colorResource(id = R.color.lightGreen)
        )

    )
}