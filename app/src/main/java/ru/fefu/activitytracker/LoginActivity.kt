package ru.fefu.activitytracker

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backBtn = findViewById<ImageButton>(R.id.back_log_btn)
        backBtn.setOnClickListener {
            finish()
        }
    }
}