package ru.fefu.activitytracker

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import ru.fefu.activitytracker.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity(R.layout.activity_registration) {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backRegBtn.setOnClickListener {
            finish()
        }

        val sexList = listOf(getString(R.string.sex_male), getString(R.string.sex_female))
        val adapter = ArrayAdapter(baseContext, android.R.layout.simple_list_item_1, sexList)
        (binding.sexRegInput.editText as? MaterialAutoCompleteTextView)?.setAdapter(adapter)

        binding.policyRegText.movementMethod = LinkMovementMethod.getInstance()

        binding.continueRegButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}