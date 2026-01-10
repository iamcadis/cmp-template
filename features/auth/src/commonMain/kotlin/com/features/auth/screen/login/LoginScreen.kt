package com.features.auth.screen.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.core.presentation.base.BaseScreen

@Composable
internal fun LoginScreen() {
    BaseScreen(
        showTopBar = false
    ) {
        LoginContent()
    }
}

@Composable
private fun LoginContent() {
    // These states would typically be provided by a ViewModel in an MVI architecture
    var email by remember { mutableStateOf("Loisbecket@gmail.com") }
    var password by remember { mutableStateOf("********") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            // TODO: Replace with AppTheme.dimens.paddingLarge
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        // App Logo
        Icon(
            // Using an AutoMirrored Shield as a placeholder for your logo
            imageVector = Icons.Filled.Shield,
            contentDescription = "App Logo",
            // TODO: Replace with AppTheme.dimens.iconSizeLarge
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        // TODO: Replace with AppTheme.dimens.spaceLarge
        Spacer(modifier = Modifier.height(32.dp))

        // Header
        Text(
            text = "Sign in to your Account",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        // TODO: Replace with AppTheme.dimens.spaceSmall
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Enter your email and password to log in",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        // TODO: Replace with AppTheme.dimens.spaceExtraLarge
        Spacer(modifier = Modifier.height(40.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        // TODO: Replace with AppTheme.dimens.spaceMedium
        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            }
        )
        // TODO: Replace with AppTheme.dimens.spaceMedium
        Spacer(modifier = Modifier.height(16.dp))

        // Remember Me & Forgot Password
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                Text(text = "Remember me", style = MaterialTheme.typography.bodyMedium)
            }
            TextButton(onClick = { /* TODO: Handle Forgot Password */ }) {
                Text("Forgot Password?")
            }
        }
        // TODO: Replace with AppTheme.dimens.spaceLarge
        Spacer(modifier = Modifier.height(24.dp))

        // Log In Button
        Button(
            onClick = { /* TODO: Handle Login */ },
            modifier = Modifier
                .fillMaxWidth()
                // TODO: Replace with AppTheme.dimens.buttonHeight
                .height(50.dp),
            // TODO: Replace with AppTheme.dimens.radiusMedium
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Log In", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        }
        // TODO: Replace with AppTheme.dimens.spaceLarge
        Spacer(modifier = Modifier.height(32.dp))

        // "Or" Divider
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = "Or",
                // TODO: Replace with AppTheme.dimens.paddingMedium
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }
        // TODO: Replace with AppTheme.dimens.spaceLarge
        Spacer(modifier = Modifier.height(32.dp))

        // Social Logins
        OutlinedButton(
            onClick = { /* TODO: Handle Google Sign In */ },
            modifier = Modifier
                .fillMaxWidth()
                // TODO: Replace with AppTheme.dimens.buttonHeight
                .height(50.dp),
            // TODO: Replace with AppTheme.dimens.radiusMedium
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
        ) {
            // TODO: Replace with your actual Google icon from resources
            // For KMP, you would typically use an expect/actual mechanism for resources.
            // Icon(painter = painterResource("drawable/ic_google.xml"), ...)
            Text("G", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.titleMedium.fontSize)
            // TODO: Replace with AppTheme.dimens.spaceSmall
            Spacer(Modifier.width(8.dp))
            Text("Continue with Google", color = MaterialTheme.colorScheme.onSurface)
        }
        // TODO: Replace with AppTheme.dimens.spaceMedium
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = { /* TODO: Handle Facebook Sign In */ },
            modifier = Modifier
                .fillMaxWidth()
                // TODO: Replace with AppTheme.dimens.buttonHeight
                .height(50.dp),
            // TODO: Replace with AppTheme.dimens.radiusMedium
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
        ) {
            // TODO: Replace with your actual Facebook icon from resources
            Text("f", color = Color(0xFF1877F2), fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.titleMedium.fontSize)
            // TODO: Replace with AppTheme.dimens.spaceSmall
            Spacer(Modifier.width(8.dp))
            Text("Continue with Facebook", color = MaterialTheme.colorScheme.onSurface)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Sign Up
        Row(
            // TODO: Replace with AppTheme.dimens.paddingMedium
            modifier = Modifier.padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Don't have an account?", style = MaterialTheme.typography.bodyMedium)
            TextButton(onClick = { /* TODO: Navigate to Sign Up */ }) {
                Text("Sign Up")
            }
        }
    }
}