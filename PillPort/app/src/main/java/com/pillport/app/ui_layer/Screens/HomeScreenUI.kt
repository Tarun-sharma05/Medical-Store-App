package com.pillport.app.ui_layer.Screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pillport.app.Data.Response.GetAllProductsResponse
import com.pillport.app.Data.Response.GetAllProductsResponseItem
import com.pillport.app.Data.User_Pref.UserPreferenceManager
import com.pillport.app.ui_layer.Navigation.Screen
import com.pillport.app.ui_layer.Navigation.ScreenRoutes
import com.pillport.app.ui_layer.ViewModel.AppViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(viewModel: AppViewModel = hiltViewModel(),navController: NavHostController) {
    val userPreferenceManager = UserPreferenceManager(navController.context)
    val userID by userPreferenceManager.userId.collectAsState(initial = null)
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val getAllProductsState by viewModel.getAllProductsState.collectAsState()
    val getSpecificProductState by viewModel.getSpecificProductState.collectAsState()
    val context = LocalContext.current


//    val searchQuery by remember { mutableStateOf("") }
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()


    Scaffold (
         modifier = Modifier
             .fillMaxSize()
             .nestedScroll(scrollBehaviour.nestedScrollConnection),
          topBar = {
            TopAppBar(
                title = { Text(text = "PillPort", textAlign = TextAlign.Center) },
                actions = {
                    IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Cart")
                          }
                         Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {navController.navigate(ScreenRoutes.ProfileScreen)}) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "Person")
                    }
                     },
                scrollBehavior = scrollBehaviour
            )
          }
    ){ innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            when {
                getAllProductsState.Loading -> {
//                    CircularProgressIndicator()
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        CircularProgressIndicator()
                    }

                }
                getAllProductsState.Error != null -> {
                    Log.d("HomeScreen Tag" , "Error ${getAllProductsState.Error.toString()}")
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(text = getAllProductsState.Error.toString())
                    }
                }
                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Search Bar
                        SearchBar(viewModel = viewModel, onProductSelected = { product ->
                            // Handle product selection (e.g., navigate to product details)
                            navController.navigate(Screen.ProductDetailScreen.createRoute(productID = product.product_id))
                        } )


//                        // Product List
//                        if (searchQuery.isEmpty()) {
//                            // Show all products when no search query is entered
//                            ProductList(products = getAllProductsState.Data?.body() ?: GetAllProductsResponse())
//                        } else {
//                            // Show filtered search results
//                            ProductList(products = searchResults)
//                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        PillPortApp()

////                        // Display all products when no search query is entered
//                        if (viewModel.searchQuery.value.isEmpty()) {
//                            ProductList(products = getAllProductsState.Data?.body() ?: GetAllProductsResponse())
//                        }
                    }
                }
            }





        }

    }


}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SearchBar(
//    viewModel: AppViewModel,
//    onProductSelected: (GetAllProductsResponseItem) -> Unit
//) {
//    val searchQuery by viewModel.searchQuery.collectAsState()
//    val searchResults by viewModel.searchResults.collectAsState()
//    var expanded by remember { mutableStateOf(false) }
//
//    SearchBar(
//        inputField = {
//            SearchBarDefaults.InputField(
//                query = searchQuery,
//                onQueryChange = { query ->
//                    viewModel.searchProducts(query)
//                },
//                onSearch = { query ->
//                    viewModel.searchProducts(query)
//                },
//                placeholder = { Text("Search products...") },
//                leadingIcon = {
//                    if (expanded) {
//                        IconButton(onClick = { expanded = false }) {
//                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
//                        }
//                    } else {
//                        Icon(Icons.Default.Search, contentDescription = "Search")
//                    }
//                },
//                trailingIcon = {
//                    if (searchQuery.isNotEmpty()) {
//                        IconButton(onClick = { viewModel.searchProducts("") }) {
//                            Icon(Icons.Default.Close, contentDescription = "Clear Search")
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth(),
//                expanded = TODO(),
//                onExpandedChange = TODO(),
//                enabled = TODO(),
//                colors = TODO(),
//                interactionSource = TODO()
//            )
//        },
//        expanded = expanded,
//        onExpandedChange = { expanded = it },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        shape = SearchBarDefaults.inputFieldShape,
//        colors = SearchBarDefaults.colors(),
//        tonalElevation = SearchBarDefaults.TonalElevation,
//        shadowElevation = SearchBarDefaults.ShadowElevation,
//        windowInsets = SearchBarDefaults.windowInsets
//    ) {
//        // Content to display search results
//        LazyColumn {
//            items(searchResults) { product ->
//                ListItem(
//                    headlineContent = { Text(text = product.product_name) },
//                    modifier = Modifier
//                        .clickable {
//                            onProductSelected(product)
//                            expanded = false // Collapse search bar on selection
//                        }
//                        .padding(8.dp)
//                )
//            }
//        }
//    }
//}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(  viewModel: AppViewModel, onProductSelected: (GetAllProductsResponseItem) -> Unit) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    SearchBar(
        inputField = {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {query ->
                    viewModel.searchProducts(query)
                    expanded = query.isNotEmpty()
                },
//                onSearch = {query ->
//                    viewModel.searchProducts(query)
//                },
                placeholder = { Text("Search...") },
                leadingIcon = {
                    if(expanded){
                    IconButton(onClick = { expanded = false}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    }else {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {viewModel.searchProducts("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear Search")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        },
        expanded = expanded,
        onExpandedChange = {expanded = it},
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        shape = SearchBarDefaults.inputFieldShape,
        colors = SearchBarDefaults.colors(),
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = SearchBarDefaults.windowInsets,
    ) {
//       LazyColumn {
//           items(searchResults){ product ->
//              ListItem(
//                  headlineContent = { Text(text = product.name) },
//                  modifier = Modifier
//                      .clickable {
//                          onProductSelected(product)
//                          onExpandedChange(false) // Collapse search bar on selection
//                      }
//                      .padding(8.dp)
//              )
//           }
//       }

        // Content to display search results
        LazyColumn {
            items(searchResults) { product ->
                ListItem(
                    headlineContent = { Text(text = product.product_name) },
                    modifier = Modifier
                        .clickable {
                            onProductSelected(product)
                            expanded = false // Collapse search bar on selection
                        }
                        .padding(8.dp)
                )
            }
        }

    }
}

@Composable
fun ProductList(products: GetAllProductsResponse) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(products) { product ->
            ProductItem(product = product)
        }
    }
}

@Composable
fun ProductItem(product: GetAllProductsResponseItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = product.product_name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "$${product.product_price}", fontSize = 14.sp)
            Text(text = "Stock: ${product.product_stock}", fontSize = 14.sp)
            Text(text = "Category: ${product.product_category}", fontSize = 14.sp)
            Text(text = "Expiry: ${product.product_expiry_date}", fontSize = 14.sp)
        }
    }
}


@Composable
fun PillPortApp() {
    Column(modifier = Modifier.fillMaxSize()) {
        //TopBar()
        //SearchBar()
        Box(modifier = Modifier.weight(1f)) {
            ScrollableCategoryGrid()
        }
       // BottomNavBar()
    }
}

//@Composable
//fun TopBar() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(text = "PillPort", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        IconButton(onClick = { /* Cart Click Action */ }) {
//            Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Cart")
//        }
//    }
//}

//@Composable
//fun SearchBar() {
//    OutlinedTextField(
//        value = "",
//        onValueChange = {},
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .clip(RoundedCornerShape(50)),
//        placeholder = { Text(text = "Search for Medicine Here") },
//        leadingIcon = {
//            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
//        }
//    )
//}

@Composable
fun ScrollableCategoryGrid() {
    val categories = listOf(
        "Personal Care & Hygiene", "Medications & Supplements",
        "Antacid and Antidiarrheals", "First Aid & Emergency Supplies",
        "Cold Cures", "Mental Health & Wellness",
        "Pediatrics & Baby Care", "Fitness & Physical Activity",
        "Dental & Oral Care", "Women's Health",
        "Chronic Disease Management", "Healthcare Technology & Apps"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        for (i in categories.indices step 2) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CategoryButton(categories[i])
                if (i + 1 < categories.size) CategoryButton(categories[i + 1])
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CategoryButton(title: String) {
    Button(
        onClick = { /* Navigate to category */ },
        modifier = Modifier
            .height(80.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors()
    ) {
        Text(text = title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}