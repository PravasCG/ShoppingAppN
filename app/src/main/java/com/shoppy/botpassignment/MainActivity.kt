package com.shoppy.botpassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.shoppy.botpassignment.navigation.ShoppingAppNavigation
import com.shoppy.botpassignment.ui.theme.ShoppingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingAppTheme {
                ShoppingApp()
            }
        }
    }
}

@Composable
fun ShoppingApp(){
    ShoppingAppNavigation()
}