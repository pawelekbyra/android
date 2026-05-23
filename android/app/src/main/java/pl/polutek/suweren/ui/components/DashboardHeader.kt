package pl.polutek.suweren.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.polutek.suweren.ui.theme.*

@Composable
fun DashboardHeader(pIndex: Int) {
    val isLow = pIndex < 30
    val isHigh = pIndex > 70

    val targetColor = when {
        isLow -> Red600
        isHigh -> Emerald400
        else -> White
    }

    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 1000)
    )

    val infiniteTransition = rememberInfiniteTransition()
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val borderColor = when {
        isLow -> Red900.copy(alpha = 0.5f)
        isHigh -> Emerald500.copy(alpha = 0.5f)
        else -> Zinc800
    }

    val backgroundColor = when {
        isLow -> Red950.copy(alpha = 0.1f)
        isHigh -> Emerald950.copy(alpha = 0.1f)
        else -> Zinc900.copy(alpha = 0.1f)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .drawBehind {
                if (isLow || isHigh) {
                    val shadowColor = if (isLow) Red900 else Emerald500
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(shadowColor.copy(alpha = glowAlpha * 0.4f), Color.Transparent)
                        )
                    )
                }
            }
            .border(1.dp, borderColor)
            .padding(vertical = 48.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "WSKAŹNIK P",
            style = Typography.labelSmall,
            color = Zinc500,
            letterSpacing = 4.sp
        )
        Text(
            text = pIndex.toString(),
            style = Typography.displayLarge.copy(
                fontSize = 96.sp,
                color = animatedColor
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = when {
                isLow -> "STATUS: TOKSYCZNY / RESET WYMAGANY"
                isHigh -> "STATUS: SUWERENNY / KLAROWNOŚĆ"
                else -> "STATUS: REGENERACJA W TOKU"
            },
            style = Typography.labelSmall,
            color = if (isLow) Red500 else if (isHigh) Emerald500 else Zinc500,
            letterSpacing = 2.sp
        )
    }
}
