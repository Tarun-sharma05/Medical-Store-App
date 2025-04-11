package com.example.medicalstoreuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.medicalstoreuser.Data.User_Pref.UserPreferenceManager
import com.example.medicalstoreuser.ui.theme.MedicalStoreUserTheme
import com.example.medicalstoreuser.ui_layer.Navigation.AppNavigation
import com.example.medicalstoreuser.ui_layer.ViewModel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
//    @get:SuppressLint("CoroutineCreationDuringComposition")
   private val viewModel by viewModels<AppViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
               viewModel.isSplashShow.value
            }
        }
        enableEdgeToEdge()
        val userPreferenceManager = UserPreferenceManager(this)
        setContent {
            MedicalStoreUserTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                        AppNavigation( userPreferenceManager)


                }
            }
        }
    }
}


