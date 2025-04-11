package com.example.medicalstore_admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.medicalstore_admin.Ui_layer.Navigation.AppNavigation
import com.example.medicalstore_admin.Ui_layer.ViewModel.AppViewModel
import com.example.medicalstore_admin.ui.theme.MedicalStore_AdminTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by  viewModels<AppViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.isSplashShow.value
            }

        }
        enableEdgeToEdge()
        setContent {
            MedicalStore_AdminTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation()


                }
            }
        }
    }
}

