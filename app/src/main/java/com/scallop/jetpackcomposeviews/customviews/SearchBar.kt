package com.scallop.jetpackcomposeviews.customviews

// https://medium.com/@sohailshah1231/custom-search-bar-using-jetpack-compose-cbc5c3a8a0b7

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scallop.jetpackcomposeviews.R


@Composable
fun MySearchBar(
    modifier : Modifier = Modifier,
    text : String,
    onTextChange : (String) -> Unit,
    placeHolder : String,
    onCloseClicked : () -> Unit,
    onSearchClicked : (String) -> Unit,
    onMicClicked : () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                BorderStroke(
                    0.1.dp,
                    SolidColor(MaterialTheme.colors.onPrimary.copy(alpha = 0.5f))
                ),
                RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    text = placeHolder,
                    color = MaterialTheme.colors.background,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotBlank()){
                        onCloseClicked()
                    }else{
                        onMicClicked()
                    }
                }) {
                    if (text.isNotBlank()){
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }else {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            singleLine = true,
            textStyle = TextStyle(
                color = MaterialTheme.colors.onBackground,
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,

                ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.background

            ),
        )
        SearchBarDivider(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 50.dp)
        )

    }
}

@Composable
fun SearchBarDivider(
    modifier: Modifier = Modifier
){
    Divider(
        modifier = modifier
            .width(1.dp)
        ,
        color = MaterialTheme.colors.primary,
        thickness = 20.dp
    )
}

@Composable
@Preview()
fun SearchPreview(){
    MaterialTheme {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(40.dp)
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            MySearchBar(
                text = "",
                onTextChange = {},
                placeHolder = stringResource(id = R.string.search_bar_placeholder),
                onCloseClicked = { /*TODO*/ },
                onSearchClicked = {},
                onMicClicked = {}
            )
        }
    }
}