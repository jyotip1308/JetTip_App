package com.android.calculatorapp.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

val IconbuttonSizeModifier = Modifier.size(40.dp)
@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
//    elevation: Dp = 4.dp
){

    Card(modifier = modifier
        .padding(4.dp)
        .clickable { onClick.invoke() }
        .then(IconbuttonSizeModifier)
        .background(backgroundColor),
        shape = CircleShape,
//       elevation = elevation
    ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize() // Center the content within the card
            ) {
                Icon(imageVector = imageVector,
                    contentDescription ="Plus or Minus Icon",
                    tint = tint
                )
            }

    }

}