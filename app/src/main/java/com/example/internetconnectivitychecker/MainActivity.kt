package com.example.internetconnectivitychecker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val internetChecker = InternetConnectivityChecker(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        internetChecker.isConnected.observe(this) { isConnected ->
            if (isConnected) {
                // Internet is connected
                // Perform actions accordingly
                Toast.makeText(this,"Internet is Connected",Toast.LENGTH_SHORT).show()
            } else {
                // No internet connection
                // Handle offline state
                Toast.makeText(this,"No Internet is Connected",Toast.LENGTH_SHORT).show()
            }
        }

        internetChecker.startMonitoring()

    }

    override fun onDestroy() {
        super.onDestroy()
        internetChecker.stopMonitoring()
    }

}