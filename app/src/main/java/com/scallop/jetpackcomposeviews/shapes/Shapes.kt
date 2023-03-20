package com.scallop.jetpackcomposeviews.shapes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

// https://saurabhpant.medium.com/custom-layout-designing-in-jetpack-compose-5abbccc74ebd

@Composable
fun LoginScreen() {
    val density = LocalDensity.current
    val loginY = density.run { 510.dp.toPx() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Signup(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .placeAt(0, 0)
        )
        Login(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .placeAt(0, loginY.roundToInt())
        )
    }
}

@Composable
fun Signup(
    modifier: Modifier
) {

    var text by remember {
        mutableStateOf("")
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                shape = CurvedShape(CurveType.LTR)
                clip = true
            }
            .background(Color.Cyan)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Name",
                modifier = Modifier.padding(start = 18.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                },
                label = {
                    Text(
                        "Enter name",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W900
                        ),
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.W900
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        "",
                        tint = Color.Black
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Email",
                modifier = Modifier.padding(start = 18.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                },
                label = {
                    Text(
                        "Enter email",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W900
                        ),
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.W900
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        "",
                        tint = Color.Black
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Password",
                modifier = Modifier.padding(start = 18.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                },
                label = {
                    Text(
                        "Enter password",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W900
                        ),
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.W900
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        "",
                        tint = Color.Black
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = true, onCheckedChange = {})
                Text(
                    text = "Agree to our terms and conditions",
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .graphicsLayer {
                            shadowElevation = 12.dp.toPx()
                            clip = true //make sure to set clip to true
                            shape = CircleShape
                        }
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.ArrowForward,
                        "",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun Login(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                shape = CurvedShape(CurveType.RTL)
                clip = true
            }
            .background(Color.Cyan)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Already have an account?",
                modifier = Modifier.padding(start = 18.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .graphicsLayer {
                        shape = RoundedCornerShape(100.dp)
                        clip = true
                    }
                    .background(Color.Blue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "LOGIN",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W900
                    )
                )
            }
        }
    }
}

@Composable
fun Modifier.placeAt(
    x: Int,
    y: Int,
) = layout { measurables, constraints ->
    val placeable = measurables.measure(constraints)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(x, y)
    }
}

class CurvedShape(private val type: CurveType) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path =
            if (type == CurveType.LTR) ltrCurve(size)
            else rtlCurve(size)
        )
    }
}

enum class CurveType {
    LTR,
    RTL
}

fun ltrCurve(size: Size) = Path().apply {
    reset()
    val width = size.width
    val height = size.height
    val radius = 100f
    val upShift = height * (1f - 0.2f)
    arcTo(
        rect = Rect(
            left = 0f,
            top = 0f,
            right = radius * 2,
            bottom = radius * 2
        ),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    lineTo(width - radius, 0f)
    arcTo(
        rect = Rect(
            left = width - radius * 2,
            top = 0f,
            right = width,
            bottom = radius * 2
        ),
        startAngleDegrees = 270f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    lineTo(width, height - (radius * 2))
    arcTo(
        rect = Rect(
            left = width - radius * 2,
            top = height - (radius * 2),
            right = width,
            bottom = height
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 115f,
        forceMoveTo = false
    )
    arcTo(
        rect = Rect(
            left = 0f,
            top = upShift - radius * 2,
            right = radius * 2,
            bottom = upShift
        ),
        startAngleDegrees = 115f,
        sweepAngleDegrees = 65f,
        forceMoveTo = false
    )
}

fun rtlCurve(size: Size) = Path().apply {
    reset()
    val width = size.width
    val height = size.height
    val radius = 100f
    val upShift = height * (1f - 0.5f)
    arcTo(
        rect = Rect(
            left = 0f,
            top = 0f,
            right = radius * 2,
            bottom = radius * 2
        ),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 110f,
        forceMoveTo = false
    )
    arcTo(
        rect = Rect(
            left = width - radius * 2,
            top = upShift - 10,
            right = width,
            bottom = upShift + radius * 2
        ),
        startAngleDegrees = -60f,
        sweepAngleDegrees = 65f,
        forceMoveTo = false
    )
    arcTo(
        rect = Rect(
            left = width - radius * 2,
            top = height - radius * 2,
            right = width,
            bottom = height
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    arcTo(
        rect = Rect(
            left = 0f,
            top = height - radius * 2,
            right = radius * 2,
            bottom = height
        ),
        startAngleDegrees = 90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
}
