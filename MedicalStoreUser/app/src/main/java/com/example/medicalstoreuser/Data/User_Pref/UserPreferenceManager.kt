package com.example.medicalstoreuser.Data.User_Pref

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore("user_preferences")

 class UserPreferenceManager(private val context: Context) {

     companion object{
         private  val USER_ID_KEY = stringPreferencesKey("user_id")
     }


//     suspend fun saveUserId(userId: String){
//         context.dataStore.edit {
//             it[USER_ID_KEY] = userId
//         }
//     }

     suspend fun saveUserId(userId: String){
         if (userId != "Bad Gateway"){
             context.dataStore.edit {
                 it[USER_ID_KEY] = userId
             }
         }else{
             Log.e("UserPref", "Bad Gateway received, not saving userId!")
         }
     }

     val userId: Flow<String?> = context.dataStore.data.map{
         it[USER_ID_KEY]
     }

     suspend fun clearUserID(){
         context.dataStore.edit {
             it.remove(USER_ID_KEY)
         }
     }

 }