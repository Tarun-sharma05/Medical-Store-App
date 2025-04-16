package com.pillport.app.ui_layer.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.pillport.app.Data.Response.GetAllProductsResponse
import com.pillport.app.Data.User_Pref.UserPreferenceManager
import com.pillport.app.ui_layer.Navigation.Screen
import com.pillport.app.ui_layer.ViewModel.AppViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreenUI(productID: String, viewModel: AppViewModel = hiltViewModel(), navController: NavController, userPreferenceManager: UserPreferenceManager) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val productState by viewModel.getSpecificProductState.collectAsState()
    val productData = productState.Data?.body()
    val getAllProductsState = viewModel.getAllProductsState.collectAsState()
    val AllProductData = getAllProductsState.value.Data?.body()?: GetAllProductsResponse()
    val addOrderState = viewModel.addOrderState.collectAsState()
    val addOrderData = addOrderState.value.Data?.body()

    val userState by viewModel.getSpecificUserState.collectAsState()
    val userData = userState.Data?.body()

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

//    val relatedProductData = getAllProductsState.value.Data?.filter {}
    val context = LocalContext.current

    ///////////////////////////////////////////////
     val user_id by viewModel.userId.collectAsState(initial = null)
//    val user_id by userPreferenceManager.userId.collectAsState(initial = null)
//    val user_id = "fb82b2f9-08fd-454b-b641-6956cce9e3e0"
     val product_id  = productData?.product_id
     var name = userData?.name
//    var name = "Tarun"
     var product_name = productData?.product_name
     var quantity by remember{mutableStateOf(1) }

    if (showBottomSheet) {
        BuyNowBottomSheet(
            sheetState = sheetState,
            onDismiss = { showBottomSheet = false },
            productName = product_name.toString(),
            productPrice = productData?.product_price ?: 0,
            onConfirm = { quantity ->
                viewModel.addOrder(
                    user_id = user_id.toString(),
                    name = userData?.name.toString(), // Get name from user data
                    product_name = product_name.toString(),
                    quantity = quantity,
                    product_id = product_id.toString()
                )
                Toast.makeText(context, "Order placed!", Toast.LENGTH_SHORT).show()
            },
            userID = user_id.toString(),
            productID = product_id.toString(),
            name = name.toString(),

        )
    }

    //////////////////////////////////////////////

    when{
        addOrderState.value.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        addOrderState.value.Data != null -> {
            Toast.makeText(context, "${addOrderState.value.Data?.body()?.message}", Toast.LENGTH_SHORT).show()

            LaunchedEffect(addOrderState.value.Data) {
////                user_id = ""
//                name = ""
//                product_name = ""
                quantity = 1
                showBottomSheet = false
//                product_id = ""
            }
            Log.d("addOrderTAG", "Success: ${addOrderState.value.Data?.body()?.toString()}, UserID: $user_id, name : ${name}, product_name: $product_name, quantity: $quantity, product_id: $product_id")

        }
        addOrderState.value.Error != null -> {
            Toast.makeText(context, "${addOrderState.value.Error.toString()}", Toast.LENGTH_SHORT).show()
            Log.e("addOrderErrorTAG", "${addOrderState.value.Error.toString()}")
        }
    }



    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productStock by remember { mutableStateOf("") }
    var productCategory by remember { mutableStateOf("") }
    var productExpiryDate by remember { mutableStateOf("") }
    var productCompany by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productImage by remember { mutableStateOf("") }

    LaunchedEffect(productID) {
        // Calling the ViewModel method to fetch the product based on productID
        viewModel.getSpecificProduct(productID)
    }

    LaunchedEffect(productData) {
        // Calling the ViewModel method to fetch the product based on productID
        productData?.let {
            productName = it.product_name
            productPrice = it.product_price.toString()
            productStock = it.product_stock.toString()
            productCategory = it.product_category
            productExpiryDate = it.product_expiry_date
            productCompany = it.product_company
            productDescription = it.product_description
            productImage = it.product_image
        }
    }


    ////////////////////////////////////////////////////////////////////////



    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Products",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                    }
                },

                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }

                },


                scrollBehavior = scrollBehaviour
            )

        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),) {

            Column(
                modifier = Modifier
                   // .fillMaxSize()
                    .padding(16.dp),
//                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            when {
                productState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                }

                productState.Error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Error: ${productState.Error}", color = Color.Red)
                    }
                }

                productData != null -> {

                    Log.d("ProductDetailsScreenUI", "Product Data: $productData")


//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp)
//                            .verticalScroll(rememberScrollState()),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
                        // Product Image
                        AsyncImage(
                            model = productData.product_image,
                            contentDescription = "Product Image",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Product Details Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                ProductDetailItem("Name", productData.product_name)
                                ProductDetailItem("Price", "₹${productData.product_price}")
                                ProductDetailItem("Stock", productData.product_stock.toString())
                                ProductDetailItem("Category", productData.product_category)
                                ProductDetailItem("Expiry Date", productData.product_expiry_date)
                                ProductDetailItem("Company", productData.product_company)
                                ProductDetailItem("Description", productData.product_description)
                            }
                        }

                        //Spacer(modifier = Modifier.height(16.dp))

                    }


                }
            }

            //////////////////////////////////////////////////
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
//                        viewModel.addOrder(user_id.toString(), name, product_name, quantity.toInt(), product_id.toString())
                         showBottomSheet = true

                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Buy Now", color = Color.White)
                }

                OutlinedButton(
                    onClick = { /* Add to Cart logic */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Add to Cart", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
/////////////////////////////////////////////////////////
            Row() {
                when {
                    getAllProductsState.value.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                    }

                    getAllProductsState.value.Error != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = getAllProductsState.value.Error.toString())
                        }
                    }

                    getAllProductsState.value.Data != null -> {


//                AllCardLazyColumn(viewModel = viewModel, navHostController = navHostController)

                       if (productData != null && getAllProductsState.value.Data != null) {
                           val relatedProductData = AllProductData.filter {
                               it.product_category == productData.product_category && it.product_id != productData.product_id
                           }

                           LazyRow(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(innerPadding),
                               contentPadding = PaddingValues(bottom = innerPadding.calculateBottomPadding() + 30.dp)
                           ) {
                               items(relatedProductData) { productDta ->
                                   RelatedProductsEachCard(
                                       productId = productDta.product_id,
                                       productName = productDta.product_name,
                                       productCategory = productDta.product_category,
                                       productPrice = productDta.product_price,
                                       Stock = productDta.product_stock,
                                       productExpiryDate = productDta.product_expiry_date,
                                       productCompany = productDta.product_company,
                                       productDescription = productDta.product_description,
                                       productImage = productDta.product_image,
                                       navController = navController
                                   )

                               }
                           }
                       }
                    }
                }
            }
        }
    }


}



@Composable
fun ProductDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}




@Composable
fun RelatedProductsEachCard(
    productId: String,
    productName: String,
    productCategory: String,
    productPrice: Int,
    Stock: Int,
    productExpiryDate: String,
    productCompany: String,
    productDescription: String,
    productImage: String,
    navController: NavController
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(Screen.ProductDetailScreen.createRoute(productID = productId))
            },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){



        Column() {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 16.dp, bottom = 8.dp)) {
                Text(text = "$productName", style = MaterialTheme.typography.titleLarge)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Fill full width of the parent
                    .height(220.dp) // Fixed height for the image
            ){
                SubcomposeAsyncImage(
                    model = productImage,
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp)) {
                Text(text = "product ID: $productId", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Price: $productPrice", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Stock: $Stock", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Category: $productCategory", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Exp Date: $productExpiryDate", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Company: $productCompany", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Description: $productDescription", style = MaterialTheme.typography.bodyMedium)
            }

        }


    }
}

////////////////////BOTTOM SHEET CONTENT /////////////////////////
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BuyNowBottomSheet(
//    sheetState: SheetState,
//    onDismiss: () -> Unit,
//    userID: String,
//
//    productID: String,
//    name: String,
//    productName: String,
//    productPrice: Int,
//    onConfirm: (Int) -> Unit,
//
//    ) {
//
//    ModalBottomSheet(
//        onDismissRequest = { onDismiss() },
//        sheetState = sheetState,
//        modifier = Modifier.fillMaxWidth(),
//    ) {
//
//        // ✅ Your Bottom Sheet Content
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text("Confirm Order", style = MaterialTheme.typography.titleLarge)
//            Spacer(Modifier.height(8.dp))
//            Text("Product: $productName")
//            Text("Quantity: $quantity")
//            Text("Price: ₹${quantity?.times(productPrice ?: 0) ?: 0}")
//            Spacer(Modifier.height(16.dp))
//
//
//        }
//    }
//}




////////////////////////////////////////////////////////

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyNowBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    userID: String,
//    quantity: Int,
    productID: String,
    name: String,
    productName: String,
    productPrice: Int,
    onConfirm: (Int) -> Unit // Pass quantity to confirm function
) {
    var localQuantity by remember { mutableStateOf(1) }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Confirm Order", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))

            Text("Product: $productName")
            Spacer(Modifier.height(4.dp))

            Text("Price per item: ₹$productPrice")
            Spacer(Modifier.height(12.dp))

            // ✅ Quantity Selector
//            QuantitySelector(
//                quantity = quantity,
//                onQuantityChange = { quantity = it }
//            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    if (localQuantity > 1) localQuantity--
                }) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease")
                }

                Text(
                    text = "$localQuantity",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                IconButton(onClick = { localQuantity++ }) {
                    Icon(Icons.Default.Add, contentDescription = "Increase")
                }
            }

            Spacer(Modifier.height(12.dp))

            Text("Total Price: ₹${localQuantity * productPrice}")

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    onConfirm(localQuantity)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Place Order")
            }
        }
    }
}











///////////////////////////////////
@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(8.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        IconButton(
            onClick = {
                if (quantity > 1) onQuantityChange(quantity - 1)
            }
        ) {
            Icon(Icons.Default.Remove, contentDescription = "Decrease")
        }

        Text(
            text = quantity.toString(),
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 18.sp
        )

        IconButton(
            onClick = { onQuantityChange(quantity + 1) }
        ) {
            Icon(Icons.Default.Add, contentDescription = "Increase")
        }
    }
}
