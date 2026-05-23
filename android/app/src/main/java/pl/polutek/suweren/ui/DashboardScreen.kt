package pl.polutek.suweren.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.polutek.suweren.ui.components.DashboardHeader
import pl.polutek.suweren.ui.components.ProgressCard
import pl.polutek.suweren.ui.components.RunningCard
import pl.polutek.suweren.ui.theme.Typography
import pl.polutek.suweren.ui.theme.Zinc500
import pl.polutek.suweren.viewmodel.SuwerenViewModel

@Composable
fun DashboardScreen(viewModel: SuwerenViewModel) {
    val data by viewModel.uiState.collectAsState()
    val pIndex by viewModel.pIndex.collectAsState()

    Scaffold(
        containerColor = pl.polutek.suweren.ui.theme.Black
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            DashboardHeader(pIndex = pIndex)

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProgressCard(
                    title = "Nikotyna",
                    state = data.nicotine,
                    onReset = { viewModel.resetNicotine() },
                    onSetDate = { /* Logic for date picker */ }
                )
                ProgressCard(
                    title = "THC",
                    state = data.thc,
                    onReset = { viewModel.resetThc() },
                    onSetDate = { /* Logic for date picker */ }
                )
                ProgressCard(
                    title = "No-Fap",
                    state = data.noFap,
                    onReset = { viewModel.resetNoFap() },
                    onSetDate = { /* Logic for date picker */ }
                )
                RunningCard(
                    km = data.runningKmThisWeek,
                    onAddKm = { viewModel.addRunningKm(1f) }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Footer()

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun Footer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(alpha = 0.3f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SYSTEM OPERACYJNY SUWEREN V0.1-ALPHA",
            style = Typography.labelSmall.copy(fontSize = 10.sp),
            letterSpacing = 4.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            listOf("BIOHACKING", "NEUROPLASTYCZNOŚĆ", "DYSCYPLINA").forEach {
                Text(
                    text = it,
                    style = Typography.labelSmall.copy(fontSize = 9.sp),
                    color = Zinc500
                )
            }
        }
    }
}
