package ru.fefu.activitytracker

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class RegistrationActivity : AppCompatActivity(R.layout.activity_registration) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backBtn = findViewById<ImageButton>(R.id.back_reg_btn)
        backBtn.setOnClickListener {
            finish()
        }

        val sexList = listOf(getString(R.string.sex_male), getString(R.string.sex_female))
        val sexInput = findViewById<TextInputLayout>(R.id.sex_reg_input)
        val adapter = ArrayAdapter(baseContext, android.R.layout.simple_list_item_1, sexList)
        (sexInput.editText as? MaterialAutoCompleteTextView)?.setAdapter(adapter)

        val privacyText = findViewById<TextView>(R.id.policy_reg_text)
        privacyText.movementMethod = LinkMovementMethod.getInstance()

        val loginBtn = findViewById<Button>(R.id.continue_reg_button)
        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}