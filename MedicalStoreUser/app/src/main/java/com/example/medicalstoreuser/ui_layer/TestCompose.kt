package com.example.medicalstoreuser.ui_layer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.medicalstoreuser.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.medicalstoreuser.ui_layer.Common.MultiColorText


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TestCompose(modifier: Modifier = Modifier){
    LazyColumn(modifier = modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(id = R.drawable.medical_app_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Enter Email") },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)

            )

            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter Your Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)

            )



            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                colors = ButtonDefaults.buttonColors(

                )

            ){
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(30.dp))
             MultiColorText("Don't have an account ?  ","SignUp")



        }
    }
}


//@Composable
//fun MultiColorText(){
//    val annotatedString = buildAnnotatedString {
//        withStyle(style = SpanStyle(color = Color.Black)){
//            append("Don't have an account ?  ")
//        }
//        withStyle(style = SpanStyle(color = Color.Blue)){
//            append(" SignUp")
//        }
//        append("!")
//    }
//    Text(text = annotatedString)
//}

