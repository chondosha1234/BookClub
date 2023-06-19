package com.chondosha.bookclub

import android.content.Intent
import android.os.Bundle
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

    private lateinit var notificationListenerIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notificationListenerIntent = Intent(this, NotificationListener::class.java)
        startService(notificationListenerIntent)

        setContent {
            BookClubTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   ProvideRepository {
                       Navigation()
                   }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(notificationListenerIntent)
    }
}
