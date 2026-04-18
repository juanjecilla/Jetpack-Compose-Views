package com.scallop.jetpackcomposeviews.customviews

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scallop.jetpackcomposeviews.R

// https://medium.com/huawei-developers/how-to-create-a-flip-card-effect-using-jetpack-compose-6bffbfd07dbd
@Composable
fun AddCreditCard(
    backgroundColor: Color = Color(0xFF1A237E),
    cardNumber: String = "**** **** **** 1234",
    cardHolder: String = "Card Holder",
    cardExpiry: String = "12/26"
) {
    var rotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    Card(
        modifier = Modifier
            .height(220.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .clickable { rotated = !rotated },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        if (!rotated) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = "contactless",
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(top = 6.dp, bottom = 6.dp, end = 20.dp)
                            .graphicsLayer { alpha = animateFront },
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = "card network",
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .graphicsLayer { alpha = animateFront },
                        tint = Color.Unspecified
                    )
                }

                Text(
                    text = cardNumber,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .graphicsLayer { alpha = animateFront },
                    fontWeight = FontWeight.Normal,
                    fontSize = 25.sp
                )

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Card Holder",
                            color = Color.Gray,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.graphicsLayer { alpha = animateFront }
                        )
                        Text(
                            text = cardHolder,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.graphicsLayer { alpha = animateFront }
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "VALID THRU",
                            color = Color.Gray,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.graphicsLayer { alpha = animateFront }
                        )
                        Text(
                            text = cardExpiry,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.graphicsLayer { alpha = animateFront }
                        )
                    }
                }
            }
        } else {
            Column(modifier = Modifier.padding(top = 20.dp)) {
                Divider(
                    modifier = Modifier.graphicsLayer { alpha = animateBack },
                    color = Color.Black,
                    thickness = 50.dp
                )

                Text(
                    text = "123",
                    color = Color.Black,
                    modifier = Modifier
                        .padding(10.dp)
                        .background(Color.White)
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = animateBack
                            rotationY = rotation
                        }
                        .padding(10.dp),
                    fontSize = 15.sp,
                    textAlign = TextAlign.End
                )

                Text(
                    text = "Tap to flip back",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = animateBack
                            rotationY = rotation
                        }
                        .padding(5.dp),
                    fontWeight = FontWeight.Thin,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
