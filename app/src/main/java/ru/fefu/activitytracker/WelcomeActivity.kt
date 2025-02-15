package ru.fefu.activitytracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import ru.fefu.activitytracker.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity(R.layout.activity_welcome) {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActivity()
    }

    private fun setupActivity() {
        binding.regButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}