package com.studyspace.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.studyspace.mobile.api.RetrofitClient
import com.studyspace.mobile.api.User
import com.studyspace.mobile.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRegisterBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            
            if (!validateInputs(username, email, password, confirmPassword)) {
                return@setOnClickListener
            }
            
            register(username, email, password)
        }
        
        binding.tvGoToLogin.setOnClickListener {
            finish()
        }
    }
    
    private fun validateInputs(username: String, email: String, password: String, confirmPassword: String): Boolean {
        when {
            username.isBlank() -> {
                Toast.makeText(this, "Enter username", Toast.LENGTH_SHORT).show()
                return false
            }
            username.length < 3 -> {
                Toast.makeText(this, "Username must be at least 3 characters", Toast.LENGTH_SHORT).show()
                return false
            }
            email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
                return false
            }
            password.isBlank() -> {
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
                return false
            }
            password.length < 6 -> {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return false
            }
            password != confirmPassword -> {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }
    
    private fun register(username: String, email: String, password: String) {
        binding.btnRegister.isEnabled = false
        
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.register(
                    User(username = username, email = email, password = password)
                )
                
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                    binding.btnRegister.isEnabled = true
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                binding.btnRegister.isEnabled = true
            }
        }
    }
}
