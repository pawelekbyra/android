package pl.polutek.suweren.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import pl.polutek.suweren.data.AddictionState
import pl.polutek.suweren.ui.theme.*
import java.time.Duration
import java.time.Instant

@Composable
fun ProgressCard(
    title: String,
    state: AddictionState,
    onReset: () -> Unit,
    onSetDate: () -> Unit
) {
    val now = remember { mutableStateOf(Instant.now()) }
    LaunchedEffect(Unit) {
        while(true) {
            delay(1000)
            now.value = Instant.now()
        }
    }

    val duration = Duration.between(state.lastReset, now.value)
    val days = duration.toDays()
    val hours = duration.toHours() % 24
    val minutes = duration.toMinutes() % 60

    val timeString = when {
        days > 0 -> "$days d $hours h"
        hours > 0 -> "$hours h $minutes m"
        else -> "$minutes m"
    }

    Column(
        modifier = Modifier
            .background(Zinc900.copy(alpha = 0.5f))
            .border(1.dp, Zinc800)
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title.uppercase(),
                style = Typography.labelSmall,
                color = Zinc500,
                letterSpacing = 2.sp
            )
            Box(
                modifier = Modifier
                    .background(Zinc800)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "STAŻ: ${state.yearsOfAddiction} LAT",
                    style = Typography.labelSmall.copy(fontSize = 9.sp),
                    color = Zinc400
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = timeString,
            style = Typography.headlineMedium,
            color = White
        )
        Text(
            text = "BEZ CZYSTEJ DOPAMINY",
            style = Typography.labelSmall.copy(fontSize = 10.sp),
            color = Zinc600
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedAction(
                text = "RESETUJ",
                onClick = onReset,
                modifier = Modifier.weight(1f),
                color = Red500
            )
            OutlinedAction(
                text = "USTAW DATĘ",
                onClick = onSetDate,
                modifier = Modifier.weight(1f),
                color = Emerald500
            )
        }
    }
}

@Composable
fun RunningCard(
    km: Float,
    onAddKm: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Zinc900.copy(alpha = 0.5f))
            .border(1.dp, Zinc800)
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "BIEGANIE",
                style = Typography.labelSmall,
                color = Zinc500,
                letterSpacing = 2.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = String.format("%.1f", km),
                style = Typography.displayLarge.copy(fontSize = 48.sp),
                color = White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "km / tydz",
                style = Typography.labelSmall,
                color = Zinc500,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedAction(
            text = "DODAJ KILOMETR",
            onClick = onAddKm,
            modifier = Modifier.fillMaxWidth(),
            color = Emerald500
        )
    }
}

@Composable
fun OutlinedAction(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Zinc400
) {
    Box(
        modifier = modifier
            .border(1.dp, Zinc700)
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}
