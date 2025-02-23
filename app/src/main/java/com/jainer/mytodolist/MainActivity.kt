package com.jainer.mytodolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.jainer.mytodolist.ui.theme.MyToDoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val homeScreenViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]

            MyToDoListTheme {
                HomeScreen(viewModel = homeScreenViewModel)
            }
        }
    }
}