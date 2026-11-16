package com.example.navigationjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    composable(route="registration"){
        RegistrationScreen{
            navController.navigate("main")
        }
    }
    composable(route="login"){
        LoginScreen(navController)
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
fun RegistrationScreen(onClick: ()->Unit) {
    Text(text= "Registration Screen",
        modifier= Modifier.padding(40.dp)
            .clickable {
                onClick()}
    )
}

@Composable
fun LoginScreen(navController: NavController) {
    Text(text= "Login Screen", Modifier.padding(40.dp).clickable {
        navController.navigate("registration")
    })


}

@Composable
fun MainScreen() {
    Text(text= "Main Screen", Modifier.padding(40.dp))
}