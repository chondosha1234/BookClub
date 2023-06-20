package com.chondosha.bookclub

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chondosha.bookclub.ui.theme.BookClubTheme

class MainActivity : ComponentActivity() {

    private var isLoggedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = applicationContext
        SharedPreferencesManager.changeNotificationCount(context, 0)

        isLoggedIn = SharedPreferencesManager.isLoggedIn(context)

        setContent {
            BookClubTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   ProvideRepository {
                       Navigation(isLoggedIn = isLoggedIn)
                   }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val context = applicationContext
        SharedPreferencesManager.changeNotificationCount(context, 0)
    }
}
