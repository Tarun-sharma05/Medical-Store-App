package com.pillport.app.ui_layer.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pillport.app.ui_layer.ViewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreenUI(viewModel: AppViewModel = hiltViewModel(), navController: NavController) {

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "PillPort", textAlign = TextAlign.Center)
                    }
                },
                scrollBehavior = scrollBehaviour
            )
        }
    ) { innerPadding ->

        Column (modifier = Modifier.padding(innerPadding).fillMaxSize()){
            Text(
                text = "...Saved Contacts...",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
            ContactList()
        }
    }

}



//@Composable
//fun ContactCard(contact: Contact) {
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A3A63)), // Fix background color
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = contact.name, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(text = "Address: ${contact.address}", color = Color.White, fontSize = 14.sp)
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(text = "Contact: ${contact.phone}", color = Color.White, fontSize = 14.sp)
//        }
//    }
//}




@Composable
fun ContactList() {
    val contacts = listOf(
        Contact("Raj Medical", "kukas, kookas , Rajasthan , 302026", "7374845221"),
        Contact("Shree Ram Medical Store", "Arya 1st old collage kukas ricco near Fairmount Hotel, Rajasthan 302028", "7375678998"),
        Contact("Sai Medical Store", "Front of Sai baba Mandir, Delhi road, Kukas , Rajasthan 302028", "9829678998")
    )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(contacts) { contact ->
            ContactCard(contact)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

//@Composable
//fun ContactCard(contact: Contact) {
//    Card(
//        shape = RoundedCornerShape(16.dp),
//////        backgroundColor = Color(0xFF1A3A63),
////        modifier = Modifier.fillMaxWidth(),
////        elevation = 8.dp
////    ) {
////        Column(modifier = Modifier.padding(16.dp)) {
////
////
////            Text(text = contact.name, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
////            Spacer(modifier = Modifier.height(4.dp))
////            Text(text = "Address: ${contact.address}", color = Color.White, fontSize = 14.sp)
////            Spacer(modifier = Modifier.height(4.dp))
////            Text(text = "Contact: ${contact.phone}", color = Color.White, fontSize = 14.sp)
////        }
////    }
////}

//@Composable
//fun ContactCard(contact: Contact) {
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        backgroundColor = Color(0xFF1A3A63),
//        modifier = Modifier.fillMaxWidth(),
//        elevation = 8.dp
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = contact.name, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(text = "Address: ${contact.address}", color = Color.White, fontSize = 14.sp)
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(text = "Contact: ${contact.phone}", color = Color.White, fontSize = 14.sp)
//        }
//    }
//}

@Composable
fun ContactCard(contact: Contact) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A3A63)),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = contact.name, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Address: ${contact.address}", color = Color.White, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Contact: ${contact.phone}", color = Color.White, fontSize = 14.sp)
        }
    }
}


@Composable
fun DiscountFAB() {
    FloatingActionButton(
        onClick = { /* Handle discount click */ },

        modifier = Modifier.background(Color.Yellow)
    ) {
        Text(text = "10%", color = Color.Black, fontWeight = FontWeight.Bold)
    }
}

data class Contact(val name: String, val address: String, val phone: String)


