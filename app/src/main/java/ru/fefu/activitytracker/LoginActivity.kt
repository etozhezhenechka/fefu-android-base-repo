package ru.fefu.activitytracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.activitytracker.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActivity()
    }

    private fun setupActivity() {
        binding.backLogBtn.setOnClickListener {
            finish()
        }

        binding.continueLogButton.setOnClickListener {
            val intent = Intent(this, TrackerActivity::class.java)
            startActivity(intent)
        }
    }
}