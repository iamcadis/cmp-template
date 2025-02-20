package com.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.theme.utilities.Validator
import com.theme.utilities.ext.derivedFilterBy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AutoCompleteField(
    value: String,
    items: List<T>,
    onValueChange: (String) -> Unit,
    filtering: (query: String, value: T) -> Boolean,
    modifier: Modifier = Modifier,
    required: Boolean = false,
    enabled: Boolean = true,
    label: String? = null,
    placeholder: String? = null,
    validators: List<Validator> = emptyList(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    itemContent: @Composable (T) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    var query by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(value))
    }
    val filtered by remember(query) {
        items.derivedFilterBy(query = query.text, predicate = filtering)
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        TextInput(
            value = query,
            onValueChange = {
                it.text.let { value ->
                    query = query.copy(text = value, selection = TextRange(value.length))
                    onValueChange.invoke(value)
                    expanded = filtered.isNotEmpty()
                }
            },
            enabled = enabled,
            required = required,
            label = label,
            placeholder = placeholder,
            keyboardOptions = keyboardOptions,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            validators = validators,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryEditable)
        )

        if (filtered.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                filtered.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            item.toString().let {
                                query = query.copy(text = it, selection = TextRange(it.length))
                                onValueChange.invoke(it)
                                expanded = false
                            }
                        },
                        text = {
                            itemContent(item)
                        }
                    )
                }
            }
        }
    }
}