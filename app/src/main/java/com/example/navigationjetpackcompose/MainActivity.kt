package com.example.navigationjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
val navController = rememberNavController()
NavHost( navController= navController, startDestination =  "splash"){
    composable(route="splash"){
        SplashScreen(navController)

    }
    composable(route="login"){
        LoginScreen(navController)
    }
    composable(route="registration/{email}/{name}", arguments = listOf(
       navArgument("email") {type= NavType.StringType},
        navArgument("name") {type= NavType.StringType}
    )){
        val email = it.arguments?.getString("email")
        val name = it.arguments?.getString("name")
        RegistrationScreen(email =email?:"", name = name?:""){
            navController.navigate("main")
        }
    }
    composable(route="main"){
        MainScreen()
    }
}
}

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000L)
        navController.navigate("login") {
            // 3. Remove splash from the back stack so pressing Back doesn't return to it
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        Modifier.fillMaxSize().background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Splash Screen", Modifier.padding(40.dp).background(Color.Yellow))
    }
}

@Composable
fun RegistrationScreen(email:String, name:String?,onClick:() ->Unit) {
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){

        Text(text= "Registration Screen",
            modifier= Modifier.padding(40.dp).clickable { onClick() }
        )
        Text(text= "$email",
            modifier= Modifier.padding(80.dp)
        )
        Text(text= "$name",
            modifier= Modifier.padding(80.dp)
        )


    }

}

@Composable
fun LoginScreen(navController: NavController) {
    val nameState =  remember{mutableStateOf("")}
    val emailState =  remember{mutableStateOf("")}
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Text(text = "Login Screen", Modifier.padding(40.dp))
        TextField(value = nameState.value,
            onValueChange = {
                nameState.value = it
            },
            label = { Text(text = "Enter Name") },
            placeholder = {})

        TextField(value = emailState.value,
            onValueChange = {
                emailState.value = it
            },
            label = { Text(text = "Enter Email") },
            placeholder = {})

        Button(onClick = {navController.navigate("registration/${emailState.value}/${nameState.value}")},
            modifier = Modifier.padding(40.dp).background(color = Color.Blue)){
            Text(text = "Go to Register Screen")
        }

    }
}

@Composable
fun MainScreen() {
    Text(text= "Main Screen", Modifier.padding(40.dp))
}