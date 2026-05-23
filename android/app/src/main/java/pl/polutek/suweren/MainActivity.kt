package pl.polutek.suweren

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.polutek.suweren.data.SuwerenRepository
import pl.polutek.suweren.ui.DashboardScreen
import pl.polutek.suweren.ui.theme.SuwerenTheme
import pl.polutek.suweren.viewmodel.SuwerenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = SuwerenRepository(applicationContext)
        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SuwerenViewModel(repository) as T
            }
        })[SuwerenViewModel::class.java]

        setContent {
            SuwerenTheme {
                DashboardScreen(viewModel = viewModel)
            }
        }
    }
}
