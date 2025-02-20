package com.app.template

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.theme.DisTheme
import com.theme.components.AsyncImage
import com.theme.components.AutoCompleteField
import com.theme.components.NumberInput
import com.theme.components.TextInput
import com.theme.utilities.Validator
import com.theme.utilities.addValidator
import com.theme.utilities.getValue
import com.theme.widgets.BottomSheet
import com.utility.NumberStyle
import com.utility.toCurrency
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    DisTheme {
        var showContent by remember { mutableStateOf(false) }

        var inputText by remember { mutableStateOf("") }
        var numberInput by remember { mutableStateOf<Double?>(null) }
        var autoInputText by remember { mutableStateOf("") }

        val validators by addValidator(
            "input" to listOf(
                Validator(
                    isError = inputText.isBlank(),
                    message = "This field required"
                ),
            ),
            "price" to listOf(
                Validator(
                    isError = numberInput == null,
                    message = "This field required"
                ),
                Validator(
                    isError = (numberInput ?: 0.0) > 100.0,
                    message = "Price can't exceed ${100.0.toCurrency()}"
                ),
            ),
        )

        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(all = DisTheme.dimens.default),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = DisTheme.dimens.default
                )
            ) {

                AsyncImage(
                    data = "https://dfstudio-d420.kxcdn.com/wordpress/wp-content/uploads/2019/06/digital_camera_photo-1080x675.jpg",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                        .clickable {
                            showContent = !showContent
                        }
                )

                TextInput(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = "Test TextInput",
                    required = true,
                    validators = validators.getValue("input"),
                    modifier = Modifier.fillMaxWidth()
                )

                NumberInput(
                    value = numberInput,
                    onValueChange = {
                        numberInput = it
                        println("NUMBER INPUT => $numberInput")
                                    },
                    label = "Test NumberInput",
                    required = true,
                    numberStyle = NumberStyle.DECIMAL,
                    validators = validators.getValue("price"),
                    modifier = Modifier.fillMaxWidth()
                )

                AutoCompleteField(
                    value = autoInputText,
                    items = listOf("Angsa", "Bebek", "Cacing", "Domba"),
                    onValueChange = { autoInputText = it },
                    filtering = { query, value ->
                        value.contains(query, ignoreCase = true)
                    },
                    required = true,
                    label = "Test AutoComplete",
                    itemContent = {
                        Text(text = it)
                    }
                )

                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }

                if (showContent) {
                    BottomSheet(
                        onDismissRequest = { showContent = false },
                    ) {
                        Text("Content bottom sheet")
                    }
                }
            }
        }
    }
}
