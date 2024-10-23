package com.example.medicalstoreuser

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.medicalstoreuser.Data.User_Pref.UserPreferenceManager
import com.example.medicalstoreuser.Data.apiProvider
import com.example.medicalstoreuser.ui.theme.MedicalStoreUserTheme
import com.example.medicalstoreuser.ui_layer.Navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userPreferenceManager = UserPreferenceManager(this)
        setContent {
            MedicalStoreUserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AppNavigation(userPreferenceManager)

//                        GlobalScope.launch(Dispatchers.IO) {
//                            val responce = apiProvider.api().getAllProducts()
//                            if (responce.isSuccessful) {
//                                val data = responce.body()
//                                Log.d("TAG", "onCreate: $data")
//
//                            }else{
//                                Log.d("TAG", "onCreate: ${responce.message()}")
//                            }
//                    }

                    }
                }
            }
        }
    }
}


