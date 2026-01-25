//package com.core.presentation.component
//
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.LocalTextStyle
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.TextFieldColors
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import com.core.presentation.data.Validator
//import kotlinx.collections.immutable.PersistentList
//import kotlinx.collections.immutable.persistentListOf
//
//@Composable
//fun PasswordInput(
//    label: String,
//    value: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    validators: PersistentList<Validator> = persistentListOf(),
//    enabled: Boolean = true,
//    readOnly: Boolean = false,
//    textStyle: TextStyle = LocalTextStyle.current,
//    placeholder: String? = null,
//    supportingText: String? = null,
//    leadingIcon: @Composable (() -> Unit)? = null,
//    prefix: @Composable (() -> Unit)? = null,
//    suffix: @Composable (() -> Unit)? = null,
//    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    shape: Shape = OutlinedTextFieldDefaults.shape,
//    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
//) {
//
//    var showPassword by remember { mutableStateOf(false) }
//    val togglePasswordVisibility = { showPassword = !showPassword }
//    val trailingIconVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//
//    TextInput(
//        label = label,
//        value = value,
//        onValueChange = onValueChange,
//        validators = validators,
//        keyboardOptions = keyboardOptions,
//        modifier = modifier,
//        enabled = enabled,
//        readOnly = readOnly,
//        textStyle = textStyle,
//        placeholder = placeholder,
//        supportingText = supportingText,
//        leadingIcon = leadingIcon,
//        trailingIcon = {
//            IconButton(onClick = togglePasswordVisibility) {
//                Icon(
//                    imageVector = trailingIconVector,
//                    contentDescription = if (showPassword) "Hide password" else "Show password"
//                )
//            }
//        },
//        prefix = prefix,
//        suffix = suffix,
//        singleLine = true,
//        interactionSource = interactionSource,
//        shape = shape,
//        colors = colors,
//        visualTransformation = if (showPassword) {
//            VisualTransformation.None
//        } else {
//            PasswordVisualTransformation()
//        },
//    )
//}